package cataract.fs

import cataract.event._
import cataract.stream.InputStream
import java.lang.Integer
import java.nio.ByteBuffer
import java.nio.file.{ Path, StandardOpenOption }
import java.nio.channels.{ AsynchronousFileChannel, CompletionHandler }
import java.nio.charset.Charset
import java.lang.Exception
import cataract.stream.WritableStream

object FileSystem {

  /**
   * Reads an entire file.
   *
   * @param path
   *             path to the file
   * @param listener
   *             a single listener
   */
  def readFile(file: Path, encoding: String = "utf8", callback: Callback) {
    val ch = AsynchronousFileChannel.open(file, StandardOpenOption.READ)
    if (ch.size > Int.MaxValue)
      callback(Error(new Exception("file size larger than maximum buffer")))
    val size = ch.size.toInt

    val buf = ByteBuffer.allocate(size)

    ch.read(buf, 0, null, new CompletionHandler[Integer, Void] {
      override def completed(bytesRead: Integer, v: Void) {
        callback(Data(buf.flip().asInstanceOf[ByteBuffer]))
      }

      override def failed(exception: Throwable, v: Void) {
        callback(Error(exception))
      }
    })
  }

  /**
   * A readable file stream.
   */
  class FileInputStream(val path: Path,
                        val bufSize: Int = 8192)(ls: Listener*) extends InputStream(AsynchronousFileChannel.open(path))(ls: _*) {
    override def open(): Unit = {
      emit(Open(this))
      readToEnd(ByteBuffer.allocate(bufSize))
    }
    override def pipe(ws: WritableStream): Unit = {}

    private[this] val ch: AsynchronousFileChannel = channel match {
      case ch: AsynchronousFileChannel => ch
    }

    lazy val size = ch.size()

    private def readToEnd(buf: ByteBuffer, pos: Long = 0L) {
      ch.read(buf, pos, null, new CompletionHandler[Integer, Void] {
        override def completed(bytesRead: Integer, v: Void) {
          emit(Data(buf.flip().asInstanceOf[ByteBuffer]))

          if (pos + bytesRead >= size)
            close()
          else
            readToEnd(ByteBuffer.allocate(bufSize), pos + bytesRead)
        }

        override def failed(exception: Throwable, v: Void) {
          emit(Error(exception))
        }
      })
    }
  }
}
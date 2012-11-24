package cataract.fs

import java.lang.Integer
import java.nio.ByteBuffer
import java.nio.file.{ Path, StandardOpenOption }
import java.nio.channels.{ AsynchronousFileChannel, CompletionHandler }
import java.nio.charset.Charset
import cataract.events.Listener
import cataract.stream._

object FileSystem {
  def readFile(file: Path, encoding: String = "utf8",
    callback: (Error, String) => Unit) {

    val ch = AsynchronousFileChannel.open(file, StandardOpenOption.READ)
    val size = ch.size()
    //val buf = ByteBuffer.allocate(size)
  }

  def createReadStream(path: Path,
    bufferSize: Int = 8 * 1024)(listener: Listener*): ReadableStream =

    new ReadableStream {
      override def readable = ch.isOpen
      override def destroy() = { emit(Close); ch.close() }
      override def pipe(ws: WritableStream) = {}
      override val listeners = listener.toList

      val charset = Charset.forName("utf-8")

      val ch = AsynchronousFileChannel.open(path)
      val size = ch.size()

      private def readToEnd(buf: ByteBuffer, pos: Long = 0L) {
        ch.read(buf, pos, null, new CompletionHandler[Integer, Void] {
          override def completed(result: Integer, v: Void) {
            emit(Data(buf.flip().asInstanceOf[ByteBuffer]))

            if (pos + result >= size)
              destroy()
            else
              readToEnd(ByteBuffer.allocate(bufferSize), pos + result)
          }

          override def failed(exception: Throwable, v: Void) {
            emit(Error(exception))
          }
        })
      }

      readToEnd(ByteBuffer.allocate(bufferSize))
    }
}
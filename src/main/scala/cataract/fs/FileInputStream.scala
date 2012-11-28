/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.fs

import cataract.event.{ Data, Error, Listener, Open }
import cataract.stream.{ InputStream, WritableStream }

import java.nio.ByteBuffer
import java.nio.channels.{ AsynchronousFileChannel, CompletionHandler }
import java.nio.file.Path

import scala.annotation.implicitNotFound

/**
 * A readable file stream.
 */
class FileInputStream protected (
    val path: Path,
    val bufSize: Int,
    ls: Seq[Listener]) extends InputStream(AsynchronousFileChannel.open(path), ls) {

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

object FileInputStream {
  def create(path: Path, bufSize: Int = 8192)(ls: Listener*) =
    new FileInputStream(path, bufSize, ls)
}
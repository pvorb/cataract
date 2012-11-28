/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.stream

import cataract.event.{ Data, Error, Listener, Open }

import java.lang.Integer
import java.nio.ByteBuffer

import java.nio.channels.{ AsynchronousByteChannel, CompletionHandler }

class ByteInputStream protected (
    protected val byteChannel: AsynchronousByteChannel,
    protected val bufferSize: Int,
    ls: Seq[Listener]) extends InputStream(byteChannel, ls) {

  override def open(): Unit = {
    emit(Open[ByteInputStream](this))
    readToEnd(ByteBuffer.allocate(bufferSize))
  }
  override def pipe(ws: WritableStream): Unit = {}

  protected def readToEnd(buf: ByteBuffer) {
    byteChannel.read(buf, null, new CompletionHandler[Integer, Void] {
      override def completed(bytesRead: Integer, v: Void) {
        if (bytesRead > 0)
          emit(Data[ByteBuffer](buf.flip().asInstanceOf[ByteBuffer]))

        if (bytesRead < bufferSize)
          close()
        else
          readToEnd(ByteBuffer.allocate(bufferSize))
      }

      override def failed(exception: Throwable, v: Void) {
        emit(Error(exception))
      }
    })
  }
}

object ByteInputStream {
  def create(byteChannel: AsynchronousByteChannel,
             bufSize: Int = 8192)(ls: Listener*) =
    new ByteInputStream(byteChannel, bufSize, ls)
}

package cataract.stream

import cataract.event._
import java.lang.Integer
import java.nio.ByteBuffer
import java.nio.channels.{ AsynchronousByteChannel, CompletionHandler }

class ByteInputStream(
  protected val byteChannel: AsynchronousByteChannel,
  protected val bufferSize: Int)(ls: Listener*)

    extends InputStream(byteChannel)(ls: _*) {

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

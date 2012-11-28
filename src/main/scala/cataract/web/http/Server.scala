package cataract.web.http

import cataract.event._
import cataract.web.{ ServerRequest, ServerResponse }
import java.net.InetSocketAddress
import java.nio.channels.{
  AsynchronousServerSocketChannel,
  AsynchronousSocketChannel,
  CompletionHandler
}

class Server(bufSize: Int = 8192) {
  def listen(addresses: InetSocketAddress*)(
    callback: Callback): Unit = {
    
    for (address <- addresses) {
      val ch = AsynchronousServerSocketChannel.open()
      ch.bind(address)
      callback(Open(address))
      ch.accept(null, new CompletionHandler[AsynchronousSocketChannel, Void] {
        override def completed(ch: AsynchronousSocketChannel, v: Void) {
          callback(Server.Request(ServerRequest.build(ch, bufSize), new ServerResponse(ch)))
        }

        override def failed(exception: Throwable, v: Void) {
          callback(Error(exception))
        }
      })
    }
  }
}

object Server {
  case class Request(request: (Listener*) => ServerRequest,
                     response: ServerResponse) extends Event
}

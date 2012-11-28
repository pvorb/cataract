/*                  __                              __ 
**   _____ ___ __ _/ /_ ___ __ _ ___ ___ __ _____ _/ /_
**  / ___// __` //  __// __` // ___// __` //  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.web.http

import cataract.event.{ Callback, Error, Event, EventEmitter, Listener, Open }
import cataract.web.{ ServerRequest, ServerResponse }
import java.net.InetSocketAddress
import java.nio.channels.{ AsynchronousServerSocketChannel, AsynchronousSocketChannel, CompletionHandler }
import cataract.event.emits

@emits(classOf[Open[Server]])
@emits(classOf[Error])
class Server protected (
    addresses: Seq[InetSocketAddress],
    bufSize: Int,
    ls: Seq[Listener]) extends EventEmitter(ls) {

  for (address <- addresses) {
    val ch = AsynchronousServerSocketChannel.open()
    ch.bind(address)
    emit(new Open(address))
    ch.accept(null, new CompletionHandler[AsynchronousSocketChannel, Void] {
      override def completed(ch: AsynchronousSocketChannel, v: Void) {
        emit(new Server.Request(ServerRequest.build(ch, bufSize),
          new ServerResponse(ch)))
      }

      override def failed(exception: Throwable, v: Void) {
        emit(new Error(exception))
      }
    })
  }
}

object Server {
  def create(addresses: InetSocketAddress*)(bufSize: Int = 8192)(ls: Listener*) =
    new Server(addresses, bufSize, ls)

  case class Request(request: (Listener*) => ServerRequest,
                     response: ServerResponse) extends Event
}

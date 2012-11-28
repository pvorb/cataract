package cataract.net.tcp

import java.net.InetSocketAddress
import cataract.event.Listener
import cataract.event.EventEmitter

class Server protected (
    addresses: Seq[InetSocketAddress],
    bufSize: Int,
    ls: Seq[Listener]) extends EventEmitter(ls) {

}

object Server {
  def create(addresses: InetSocketAddress*)(bufSize: Int)(ls: Listener*) =
    new Server(addresses, bufSize, ls)
}

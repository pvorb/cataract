/*                  __                              __ 
**   _____ ___ __ _/ /_ ___ __ _ ___ ___ __ _____ _/ /_
**  / ___// __` //  __// __` // ___// __` //  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

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

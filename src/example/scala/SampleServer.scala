/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

import cataract.event.Open
import cataract.web.http.Server
import cataract.web.http.Server.Request
import java.net.InetSocketAddress

object SampleServer extends App {
  Server.create(new InetSocketAddress("localhost", 1337))() {
    case Open(address: InetSocketAddress) => println("Listening on " + address)
    case Request(request, response) => request {
      case x => println(x)
    }
    case x => println(x)
  }

  Thread.currentThread().join()
}

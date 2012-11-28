package cataract.example.http

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

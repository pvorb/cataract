/*                  __                              __ 
**   _____ ___ __ _/ /_ ___ __ _ ___ ___ __ _____ _/ /_
**  / ___// __` //  __// __` // ___// __` //  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.web

import cataract.stream.{ ByteInputStream, WritableStream }
import cataract.event._
import java.nio.ByteBuffer
import java.nio.channels.{
  AsynchronousByteChannel,
  AsynchronousSocketChannel,
  CompletionHandler
}
import java.nio.charset.Charset
import java.net.URI
import scala.collection.mutable
import java.text.ParseException

class ServerRequest private (
    ch: AsynchronousSocketChannel,
    bufferSize: Int,
    ls: Seq[Listener]) extends ByteInputStream(ch, bufferSize, ls) {

  def method = head._1
  def uri = head._2
  def httpVersion = head._3
  def headers = head._4
  
  override def open() {
    head
  }

  private[this] lazy val head: (String, URI, String, Map[String, String]) = {
    emit(new Open(this))

    val charset = Charset.forName("ISO-8859-1")
    val head = new StringBuilder

    def readHead(buf: ByteBuffer): Unit = {
      val future = byteChannel.read(buf)
      val bytesRead = future.get()
      charset.decode(buf.flip().asInstanceOf[ByteBuffer]).toString
        .split("\r\n\r\n", 2) match {
          case Array(h) => {
            head.append(h)

            if (bytesRead < bufferSize)
              close()
            else
              readHead(ByteBuffer.allocate(bufferSize))
          }
          case Array(h, t) => {
            head.append(h)
            emit(new Data(ByteBuffer.wrap(t.getBytes(charset))))

            if (bytesRead < bufferSize)
              close()
            else {
              readToEnd(ByteBuffer.allocate(bufferSize))
            }
          }
        }
    }

    def parseHead(head: String): (String, URI, String, Map[String, String]) = {
      val lines = head.split("\r\n").iterator
      val nonEmpty = lines.dropWhile(_.isEmpty)

      val RequestLine(method, uri, version) =
        if (nonEmpty.hasNext) nonEmpty.next
        else ""

      val headers = new mutable.HashMap[String, String]

      var lastName = ""
      var i = 0
      while (nonEmpty.hasNext) {
        val line = nonEmpty.next

        if (line.startsWith(" ") || line.startsWith("\t")) {
          if (i == 0)
            throw new ParseException("whitespace in first header name", 0)
          headers(lastName) = line
        } else {
          var Array(name, value) = line.split(":", 2)
          name = name.toLowerCase
          headers(name) = value.replaceFirst("^\\s+", "")
          lastName = name
        }
      }

      (method, uri, version, headers.toMap)
    }

    try {
      parseHead(head.result)
    } catch {
      case e => {
        emit(new Error(e))

        ("GET", new URI("/"), "1.1", Map())
      }
    }
  }

  head
}

object ServerRequest {
  private[cataract] def build(channel: AsynchronousSocketChannel,
                              bufSize: Int = 8192)(ls: Listener*) =
    new ServerRequest(channel, bufSize, ls)
}
package cataract.web

import java.net.URI
import java.net.URISyntaxException

object RequestLine {
  def apply(method: String, uri: URI, version: String): String =
    method + " " + uri.toASCIIString() + " HTTP/" + version

  def unapply(line: String): Option[(String, URI, String)] =
    """(\p{Upper}+) ([^ ]+) HTTP/(.+)""".r.unapplySeq(line) match {
      case Some(List(Method(method), url, version)) =>
        try {
          Some(Method(method), new URI(url), version)
        } catch {
          case e => None
        }
      case _ => None
    }
}

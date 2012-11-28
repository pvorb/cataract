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

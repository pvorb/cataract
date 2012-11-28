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

object ResponseLine {
  def apply(version: String, statusCode: Int, statusName: String): String =
    "HTTP/" + version + " " + statusCode + " " + statusName

  def unapply(line: String): Option[(String, Int, String)] =
    """HTTP/([^ ]+) (\d+) (.+)""".r.unapplySeq(line) match {
      case Some(List(version, statusCode, statusName)) =>
        Some(version, statusCode.toInt, statusName)
      case _ => None
    }
}

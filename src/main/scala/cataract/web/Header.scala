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

object Header {
  def apply(name: String, value: String): String = name + ": " + value
  def unapply(str: String): Option[(String, String)] =
    """([^:]+):(.+)""".r.unapplySeq(str) match {
      case Some(List(name, value)) => Some(name.trim(), value.trim())
      case _ => None
    }
}

/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.web

object Method {
  def apply(method: String): String = method
  def unapply(method: String): Option[String] = """(\p{Upper}+)""".r
    .unapplySeq(method) match {
      case Some(_) => Some(method)
      case _ => None
    }

  lazy val GET = Method("GET")
  lazy val POST = Method("POST")
  lazy val HEAD = Method("HEAD")
  lazy val PUT = Method("PUT")
  lazy val DELETE = Method("DELETE")
}

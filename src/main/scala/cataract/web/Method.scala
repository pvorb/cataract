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

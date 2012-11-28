package cataract.web

object Header {
  def apply(name: String, value: String): String = name + ": " + value
  def unapply(str: String): Option[(String, String)] =
    """([^:]+):(.+)""".r.unapplySeq(str) match {
      case Some(List(name, value)) => Some(name.trim(), value.trim())
      case _ => None
    }
}

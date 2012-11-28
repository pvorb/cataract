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

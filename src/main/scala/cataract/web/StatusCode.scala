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

object StatusCode {
  def apply(code: Int, name: String): String = code + " " + name
  def unapply(arg: String): Option[(Int, String)] =
    """^(\d\d\d) ([^\r\n]+)""".r.unapplySeq(arg) match {
      case Some(List(code, name)) =>
        try {
          Some((code.toInt, name))
        } catch {
          case _: NumberFormatException => None
        }
      case _ => None
    }
  
  // Informational 1XX
  lazy val Continue = StatusCode(100, "Continue")
  lazy val SwitchingProtocols = StatusCode(101, "Switching Protocols")

  // Successful 2XX
  lazy val OK = StatusCode(200, "OK")
  lazy val Created = StatusCode(201, "Created")
  lazy val Accepted = StatusCode(202, "Accepted")
  lazy val NonAuthoritiveInformation = StatusCode(203, "Non-Authoritive Information")
  lazy val NoContent = StatusCode(204, "No Content")
  lazy val ResetContent = StatusCode(205, "Reset Content")
  lazy val PartialContent = StatusCode(206, "Partial Content")

  // Redirection 3XX
  lazy val MultipleChoices = StatusCode(300, "Multiple Choices")
  lazy val MovedPermanently = StatusCode(301, "Moved Permanently")
  lazy val Found = StatusCode(302, "Found")
  lazy val SeeOther = StatusCode(303, "See Other")
  lazy val NotModified = StatusCode(304, "Not Modified")
  lazy val UseProxy = StatusCode(305, "Use Proxy")
  lazy val TemporaryRedirect = StatusCode(307, "Temporary Redirect")

  // Client Error 4XX
  lazy val BadRequest = StatusCode(400, "Bad Request")
  lazy val Unauthorized = StatusCode(401, "Unauthorized")
  lazy val PaymentRequired = StatusCode(402, "Payment Required")
  lazy val Forbidden = StatusCode(403, "Forbidden")
  lazy val NotFound = StatusCode(404, "Not found")
  lazy val MethodNotAllowed = StatusCode(405, "Method Not Allowed")
  lazy val NotAcceptable = StatusCode(406, "Not Acceptable")
  lazy val ProxyAuthenticationRequired =
    StatusCode(407, "Proxy Authentication Required")
  lazy val RequestTimeout = StatusCode(407, "Request Timeout")
  lazy val Conflict = StatusCode(409, "Conflict")
  lazy val Gone = StatusCode(410, "Gone")
  lazy val LengthRequired = StatusCode(411, "Length Required")
  lazy val PreconditionFailed = StatusCode(412, "PreconditionFailed")
  lazy val RequestEntityTooLarge = StatusCode(413, "RequestEntityTooLarge")
  lazy val RequestURITooLong = StatusCode(414, "Request-URI Too Long")
  lazy val UnsupportedMediaType = StatusCode(415, "Unsupported Media Type")
  lazy val RequestRangeNotSatisfiable =
    StatusCode(416, "Request Range Not Satisfiable")
  lazy val ExpectationFailed = StatusCode(417, "ExpectationFailed")

  // Server Error 5XX
  lazy val InternalServerError = StatusCode(500, "Internal Server Error")
  lazy val NotImplemented = StatusCode(501, "Not Implemented")
  lazy val BadGateway = StatusCode(502, "Bat Gateway")
  lazy val ServiceUnavailable = StatusCode(503, "Service Unavailable")
  lazy val GatewayTimeout = StatusCode(504, "Gateway Timeout")
  lazy val HTTPVersionNotSupported = StatusCode(505, "HTTP Version Not Supported")
}

package cataract.web

case object StatusCodes {
  abstract case class StatusCode(code: Int, name: String) {
    override def toString = code + " " + name
  }

  // Informational 1XX
  case object Continue extends StatusCode(100, "Continue")
  case object SwitchingProtocols extends StatusCode(101, "Switching Protocols")

  // Successful 2XX
  case object OK extends StatusCode(200, "OK")
  case object Created extends StatusCode(201, "Created")
  case object Accepted extends StatusCode(202, "Accepted")
  case object NonAuthoritiveInformation
    extends StatusCode(203, "Non-Authoritive Information")
  case object NoContent extends StatusCode(204, "No Content")
  case object ResetContent extends StatusCode(205, "Reset Content")
  case object PartialContent extends StatusCode(206, "Partial Content")

  // Redirection 3XX
  case object MultipleChoices extends StatusCode(300, "Multiple Choices")
  case object MovedPermanently extends StatusCode(301, "Moved Permanently")
  case object Found extends StatusCode(302, "Found")
  case object SeeOther extends StatusCode(303, "See Other")
  case object NotModified extends StatusCode(304, "Not Modified")
  case object UseProxy extends StatusCode(305, "Use Proxy")
  case object TemporaryRedirect extends StatusCode(307, "Temporary Redirect")

  // Client Error 4XX
  case object BadRequest extends StatusCode(400, "Bad Request")
  case object Unauthorized extends StatusCode(401, "Unauthorized")
  case object PaymentRequired extends StatusCode(402, "Payment Required")
  case object Forbidden extends StatusCode(403, "Forbidden")
  case object NotFound extends StatusCode(404, "Not found")
  case object MethodNotAllowed extends StatusCode(405, "Method Not Allowed")
  case object NotAcceptable extends StatusCode(406, "Not Acceptable")
  case object ProxyAuthenticationRequired
    extends StatusCode(407, "Proxy Authentication Required")
  case object RequestTimeout extends StatusCode(407, "Request Timeout")
  case object Conflict extends StatusCode(409, "Conflict")
  case object Gone extends StatusCode(410, "Gone")
  case object LengthRequired extends StatusCode(411, "Length Required")
  case object PreconditionFailed extends StatusCode(412, "PreconditionFailed")
  case object RequestEntityTooLarge
    extends StatusCode(413, "RequestEntityTooLarge")
  case object RequestURITooLong extends StatusCode(414, "Request-URI Too Long")
  case object UnsupportedMediaType
    extends StatusCode(415, "Unsupported Media Type")
  case object RequestRangeNotSatisfiable
    extends StatusCode(416, "Request Range Not Satisfiable")
  case object ExpectationFailed extends StatusCode(417, "ExpectationFailed")

  // Server Error 5XX
  case object InternalServerError
    extends StatusCode(500, "Internal Server Error")
  case object NotImplemented extends StatusCode(501, "Not Implemented")
  case object BadGateway extends StatusCode(502, "Bat Gateway")
  case object ServiceUnavailable extends StatusCode(503, "Service Unavailable")
  case object GatewayTimeout extends StatusCode(504, "Gateway Timeout")
  case object HTTPVersionNotSupported
    extends StatusCode(505, "HTTP Version Not Supported")
}

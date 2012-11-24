package cataract

import cataract.events.Event

package object stream {
  // common events
  case object Close extends Event
  case class Error(exception: Throwable) extends Event
}

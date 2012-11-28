package cataract

import java.nio.Buffer

package object event {
  class Event
  type Listener = Event => Unit
  type Callback = Listener
  
  case class Open[+T](connection: T) extends Event
  case class Close[+T](connection: T) extends Event
  case class Data[+T](buf: T) extends Event
  case class Error(exc: Throwable) extends Event
}
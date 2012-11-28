/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract

package object event {
  class Event
  type Listener = Event => Unit
  type Callback = Listener

  case class Open[+T](connection: T) extends Event
  case class Close[+T](connection: T) extends Event
  case class Data[+T](buf: T) extends Event
  case class Error(exc: Throwable) extends Event
}
package cataract

package object events {
  abstract class Event
  type Listener = Event => Unit
}
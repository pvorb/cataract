package cataract.events

abstract class EventEmitter {
  val listeners: List[Listener]

  def emit(ev: Event): Unit = for (l <- listeners) l(ev)
}

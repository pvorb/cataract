package cataract.event

import java.nio.channels.{ AsynchronousByteChannel, CompletionHandler }
import java.nio.ByteBuffer

abstract class EventEmitter(ls: Listener*) {
  val listeners: List[Listener] = ls.toList
  def emit(ev: Event): Unit = for (l <- listeners) l(ev)
}

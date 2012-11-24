package cataract.stream

import cataract.events.Event
import cataract.events.EventEmitter
import java.nio.ByteBuffer

case class Pipe(source: ReadableStream) extends Event

trait WritableStream extends EventEmitter {
  def write(buffer: ByteBuffer): Unit
  def end(): Unit
  def destroy(): Unit
}

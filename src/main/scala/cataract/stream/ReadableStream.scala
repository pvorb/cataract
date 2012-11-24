package cataract.stream

import cataract.events.Event
import cataract.events.EventEmitter
import cataract.events.Listener
import java.nio.ByteBuffer
import java.nio.charset.Charset

case object Open
case class Data(buffer: ByteBuffer) extends Event
case object End extends Event

abstract class ReadableStream extends EventEmitter {
  def readable: Boolean
  def destroy(): Unit
  def pipe(ws: WritableStream): Unit
}

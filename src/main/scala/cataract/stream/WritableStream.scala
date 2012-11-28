package cataract.stream

import cataract.event._
import java.nio.ByteBuffer

case class Pipe(source: InputStream) extends Event

trait WritableStream extends EventEmitter {
  def write(buffer: ByteBuffer): Unit
  def end(): Unit
  def destroy(): Unit
}

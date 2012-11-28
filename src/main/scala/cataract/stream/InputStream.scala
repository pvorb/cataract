package cataract.stream

import cataract.event._
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.channels.AsynchronousChannel
import java.nio.channels.CompletionHandler
import java.lang.Integer

abstract class InputStream(
  protected val channel: AsynchronousChannel)(ls: Listener*)
    extends EventEmitter(ls: _*) {
  def readable: Boolean = channel.isOpen
  def open(): Unit
  def close(): Unit = { emit(Close(this)); channel.close() }
  def pipe(ws: WritableStream): Unit
}

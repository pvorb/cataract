/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.stream

import cataract.event._
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.channels.AsynchronousChannel
import java.nio.channels.CompletionHandler
import java.lang.Integer

abstract class InputStream(
    protected val channel: AsynchronousChannel,
    ls: Seq[Listener]) extends EventEmitter(ls) {

  def readable: Boolean = channel.isOpen
  def open(): Unit
  def close(): Unit = { emit(Close(this)); channel.close() }
  def pipe(ws: WritableStream): Unit
}

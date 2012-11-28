/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

package cataract.fs

import cataract.event.{ Data, Error, Listener, Callback, Open }
import cataract.stream.{ InputStream, WritableStream }

import java.lang.{ Exception, Integer }
import java.nio.ByteBuffer
import java.nio.channels.{ AsynchronousFileChannel, CompletionHandler }
import java.nio.file.{ Path, StandardOpenOption }

object FileSystem {

  /**
   * Reads an entire file.
   *
   * @param path
   *             path to the file
   * @param listener
   *             a single listener
   */
  def readFile(file: Path, encoding: String = "utf8", callback: Callback) {
    val ch = AsynchronousFileChannel.open(file, StandardOpenOption.READ)
    if (ch.size > Int.MaxValue)
      callback(Error(new Exception("file size larger than maximum buffer")))
    val size = ch.size.toInt

    val buf = ByteBuffer.allocate(size)

    ch.read(buf, 0, null, new CompletionHandler[Integer, Void] {
      override def completed(bytesRead: Integer, v: Void) {
        callback(Data(buf.flip().asInstanceOf[ByteBuffer]))
      }

      override def failed(exception: Throwable, v: Void) {
        callback(Error(exception))
      }
    })
  }
}
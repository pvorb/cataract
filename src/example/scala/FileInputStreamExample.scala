/*                  __                              __ 
**   _____ ____ _ _/ /_ ____ _ _ ___ ____ _ _____ _/ /_
**  / ___// __ `//  __// __ `// ___// __ `//  __//  __/
** / /__ / /_/ / / /_ / /_/ // /   / /_/ // /__  / /_
** \___/ \__,_/  \__/ \__,_//_/    \__,_/ \___/  \__/
**
**                                 Cataract Evented I/O
**                               (c) 2012, Paul Vorbach
*/

import cataract.event.Data
import cataract.fs.FileInputStream

import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Paths

object FileInputStreamExample extends App {

  val utf8Decoder = Charset.forName("UTF-8").newDecoder()

  FileInputStream.create(Paths.get("src/test/resources", "test.txt")) {
    case Data(buf: ByteBuffer) => {
      utf8Decoder.reset()
      print(utf8Decoder.decode(buf))
    }
    case _ => System.exit(0)
  }

  Thread.currentThread().join()
}

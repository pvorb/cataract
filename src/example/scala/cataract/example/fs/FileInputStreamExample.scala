package cataract.example.fs

import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Paths

import cataract.event.Data
import cataract.fs.FileInputStream

/**
 *
 *
 * @author Paul Vorbach
 */
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

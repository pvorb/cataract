package cataract.stream

import cataract.fs.FileSystem
import java.nio.file.Paths
import java.nio.charset.Charset

object ReadStreamTest extends App {

  val utf8Decoder = Charset.forName("UTF-8").newDecoder()

  val rs = FileSystem.createReadStream(Paths.get("src/test/resources",
    "test.txt")) {
    case Data(buf) => {
      utf8Decoder.reset();
      print(utf8Decoder.decode(buf))
    }

    case _ =>
  }

  while (Thread.currentThread().isAlive()) Thread.sleep(100)
}

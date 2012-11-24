package cataract.stream

import cataract.fs.FileSystem
import java.nio.file.Paths
import java.nio.charset.Charset

object ReadStreamExample extends App {

  val utf8Decoder = Charset.forName("UTF-8").newDecoder()

  val rs = FileSystem.createReadStream(Paths.get("src/test/resources",
    "test.txt")) {

    case Data(buf) => {
      utf8Decoder.reset()
      print(utf8Decoder.decode(buf))
    }

    case _ => System.exit(0)
  }

  Thread.currentThread().join()
}

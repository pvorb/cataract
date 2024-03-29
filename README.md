cataract
========

This is an attempt to build a platform similar to Node.js for the JVM using the
Scala programming language. This project is in a pre-alpha state, so you might
be interested in using alternatives.

There already is an equivalent project, called [Vert.x](http://vertx.io).

Mailing Lists
-------------

  * [User list](https://groups.google.com/group/cataract-user) -- for questions
    about the usage of the framework
  * [Developer list](https://groups.google.com/group/cataract-dev) -- for
    discussion on the future development of the framework

Example
-------

Here's an example that shows you how to read from a file with cataract. You can
find more examples in
[src/example/scala](//github.com/pvorb/cataract/tree/master/src/example/scala).

~~~ scala
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
~~~

License
-------

(The MIT License)

Copyright © 2012 Paul Vorbach

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the “Software”), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package json

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:22 19-8-16
  * @Description:
  */
import org.json4s._
import org.json4s.jackson.JsonMethods._

class Book(val author: String,val content: String,val id: String, val time: Long, val title: String)

object jsonTest {
  def main(args: Array[String]) {
    val json = "{\"author\":\"hll\",\"content\":\"ES-etamsports\",\"id\":\"693\",\"time\":1490165237200,\"title\":\"百度百科\"}"

    implicit val formats = DefaultFormats
    val book: Book = parse(json).extract[Book]
    println(book.author)
  }
}

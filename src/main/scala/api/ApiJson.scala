package api

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:35 19-8-16
  * @Description:
  */

import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import io.circe.generic.auto._
import json.FailFastCirceSupport

case class Test(id: Int, score: Int)

object ApiJson {
  def apply(): ApiJson = new ApiJson()
}

class ApiJson() extends Api with FailFastCirceSupport {
  override def routes: Route = tR

  val tR: Route = path("api" / "test") {
    post {
      entity(as[Seq[Test]]) { req =>
        complete(req)
      }
    }
  }
}
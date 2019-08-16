import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import api.{Api, ApiJson}
import context.GlobalContext

import scala.concurrent.Future

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:53 19-8-16
  * @Description:
  **/

object httpService {
  def apply()(implicit ctx: GlobalContext): httpService = new httpService()
}

class httpService()(implicit ctx: GlobalContext) {

  implicit val system: ActorSystem = ctx.system

  implicit val mat: Materializer = ctx.mat

  import scala.concurrent.ExecutionContext.Implicits.global

  val logger = com.typesafe.scalalogging.Logger(getClass)

  def server(): Future[Http.ServerBinding] = {
    val address = ctx.system.settings.config.getString("http.host")
    val port = ctx.system.settings.config.getInt("http.port")
    val apis: List[Api] = List(
      ApiJson()
    )

    val routes: Route = apis.map(_.routes).reduceLeft(_ ~ _)

    val bindingFuture = Http().bindAndHandle(routes, address, port)
    bindingFuture
  }

}

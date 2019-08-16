import java.util.UUID

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import context.GlobalContext
import database.{DatabaseComponent, DatabaseExtension}

/**
  * @Author: chenjinxin
  * @Date: Created in 下午4:59 19-8-16
  * @Description:
  */

object Main extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val system: ActorSystem = ActorSystem("system")

  implicit val mat: ActorMaterializer = ActorMaterializer()

  implicit val ctx: GlobalContext = GlobalContext()

  httpService().server

}

package context

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.typesafe.config.{Config, ConfigFactory}
import database.{DatabaseComponent, DatabaseExtension}

/**
  * @Author: chenjinxin
  * @Date: Created in 上午11:44 19-8-16
  * @Description:
  */
object GlobalContext {
  def apply()(implicit system: ActorSystem, mat: Materializer): GlobalContext = {
    val config = ConfigFactory.load()
    implicit val dc: DatabaseComponent = DatabaseExtension(system).databaseComponent
    GlobalContext(config = config, system = system, mat = mat, dc = dc)
  }
}
final case class GlobalContext(
                                config: Config
                                , system: ActorSystem
                                , mat: Materializer
                                , dc: DatabaseComponent
                              ) {

}
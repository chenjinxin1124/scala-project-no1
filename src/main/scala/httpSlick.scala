import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps
import scala.util.{Failure, Success}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, get, parameters, path, pathPrefix, _}
import akka.stream.ActorMaterializer
import io.circe.generic.auto._
import io.circe.syntax._
import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

import json.Score

class Scores(tag: Tag) extends Table[(Int, Int)](tag, "scores") {
  def id = column[Int]("id", O.PrimaryKey)

  def score = column[Int]("score")

  def * = (id, score)
}

object httpSlick extends App {

  import database.DatabaseComponent._

  //  val db = Database.forConfig("database.main")
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  val scores = TableQuery[Scores]

  val route =
    pathPrefix("select") {
      path("scores") {
        (get & parameters('start.as[Int] ? -1, 'end.as[Int] ? 10)) { (start, end) =>
          val q = scores.filter(_.id > start)
          val res: Future[Seq[(Int, Int)]] = db.run(q.result)
          onComplete(res) {
            case Success(x) => {
              complete {
                x.map(a => new Score(a._1, a._2)).asJson.noSpaces
              }
            }
            case Failure(e) => complete(e.toString)
          }
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
}
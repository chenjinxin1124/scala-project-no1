package api

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import context.GlobalContext
import database.DatabaseComponent
import io.circe.generic.auto._
import io.circe.syntax._
import json.{FailFastCirceSupport, Score}
import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery

/**
  * @Author: chenjinxin
  * @Date: Created in 下午5:32 19-8-16
  * @Description:
  */
case class ScoresCase(id: Int, score: Int)

class Scores(tag: Tag) extends Table[ScoresCase](tag, "scores") {
  def id = column[Int]("id", O.PrimaryKey)

  def score = column[Int]("score")

  def * = (id, score) <> (ScoresCase.tupled, ScoresCase.unapply)
}

object ScoresApi {
  def apply()(implicit ctx: GlobalContext, ec: ExecutionContext): ScoresApi = new ScoresApi()
}

class ScoresApi()(implicit ctx: GlobalContext, ec: ExecutionContext) extends Api with FailFastCirceSupport {

  import database.DatabaseComponent._

  private[this] implicit val system: ActorSystem = ctx.system
  private[this] implicit val mat: Materializer = ctx.mat
  private[this] implicit val dc: DatabaseComponent = ctx.dc

  val scores = TableQuery[Scores]

  override def routes: Route = insert ~ delete ~ update ~ select

  def insert: Route = path("scores" / "insert") {
    post {
      entity(as[Seq[ScoresCase]]) { req =>
        req.foreach(a => {
          onComplete{
            val q = scores.insertOrUpdate(ScoresCase(a.id, a.score))
            db.run(q)
          }{
            case Success(r) =>
              complete(r)
            case Failure(e) =>
              complete(e.printStackTrace())
          }
        })
        complete(req)
      }
    }
  }
  /*def insert: Route = path("scores" / "insert") {
    post {
      entity(as[Seq[Test]]) { req =>
        req.foreach(a => {
          val q = scores.insertOrUpdate(ScoresCase(a.id, a.score))
          val res: Future[Int] = db.run(q)
          val sql: String = q.statements.head
        })
        complete(req)
      }
    }
  }*/

  //  def delete: Route = path("scores" / "delete") {
  //    (get & parameters('id.as[Int] ? -1)) { (id) =>
  //      val q = scores.filter(_.id === id).delete
  //      val res = db.run(q)
  //      complete(q.statements.head)
  //    }
  //  }

  def delete: Route = path("scores" / "delete") {
    (get & parameters('id.as[String] ? "-1")) { (id) =>
      val arr = id.split(",").map(_.toInt).toList
      val q = scores.filter(s => s.id inSetBind arr).delete
      val res = db.run(q)
      complete(q.statements.head)
    }
  }

  def update: Route = path("scores" / "update") {
    post {
      entity(as[Test]) { req =>
        val q = scores.insertOrUpdate(ScoresCase(req.id, req.score))
        val res = db.run(q)
        complete(q.statements.head)
      }
    }
  }

  def select: Route = path("scores" / "select") {
    (get & parameters('start.as[Int] ? -1, 'end.as[Int] ? 100)) { (start, end) =>
      val q = scores.filter(_.id > start)
      val res: Future[Seq[ScoresCase]] = db.run(q.result)
      onComplete(res) {
        case Success(x) => {
          complete {
            x.map(a => new Score(a.id, a.score)).asJson.noSpaces
          }
        }
        case Failure(e) => complete(e.toString)
      }
    }
  }
}
/*
package api

import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, path, _}
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import context.GlobalContext
import database.DatabaseComponent
import json.Score

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

object ScoresApi {
  def apply()(implicit ctx: GlobalContext, ec: ExecutionContext) = new ScoresApi()
}

class ScoresApi()(implicit ctx: GlobalContext, ec: ExecutionContext) extends Api {

  private[this] implicit val system: ActorSystem = ctx.system
  private[this] implicit val mat: Materializer = ctx.mat
  private[this] implicit val dc: DatabaseComponent = ctx.dc

  val routes: Route = insert ~ delete
  def insert: Route = path("insert" / "scores") {
    extractClientIP{ ip =>
      post {
        entity(as[Score]) { request =>
          complete {
            request.toString
          }
        }
      }
    }
  }

  def delete: Route = path("delete" / "scores") {
    post {
      entity(as[Score]) { request =>
        complete {
          request.toString
        }
      }
    }
  }

}*/

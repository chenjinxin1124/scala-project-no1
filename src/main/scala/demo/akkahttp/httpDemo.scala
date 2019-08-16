package demo.akkahttp

class HttpServerExampleSpec {

}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global

object httpDemo extends App {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()

    val route =
      pathPrefix("select") {
        path("t1") {
          (get & parameters('start.as[Int] ? -1, 'end.as[Int] ? 10)) { (start, end) =>
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>$start $end</h1>"))
          }
        }
      }


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/select/t1\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

}
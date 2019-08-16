package api

import akka.http.scaladsl.server.Route

/**
  * @Author: chenjinxin
  * @Date: Created in 下午3:06 19-8-16
  * @Description:
  */
trait Api {

  def routes: Route

  sealed trait ApiResult

  final case class Ok(status: Int = 0) extends ApiResult

  final case class Good[T](status: Int = 0, data: T) extends ApiResult

  object Good {
    def apply[T](data: T): Good[T] = Good(0, data)
  }

  final case class Error(status: Int, message: Option[String]) extends ApiResult

}

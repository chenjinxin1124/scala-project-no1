package demo

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * @Author: chenjinxin
  * @Date: Created in 下午2:24 19-8-19
  * @Description:
  */
object OnComplete{
  import scala.util._

  def f(x: Int)(y: Int) = x + y

  f(1)(2)
  f (1) (2)
  f {1} {2}//相当于把大括号内部的代码最后的返回值作为一个相应的参数
  f({1})({2})
  f ({1}) ({2})

  /*def g[A](x: Future[A])(p: PartialFunction[Try[A], A])(implicit ec: ExecutionContext): Future[A] = {
    val q = Promise[A]
    x onComplete {
      case Success(u) => q.success(p(u))
      case Failure(e) => q.success(p(e))
    }
    q.future
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  g(Future.successful(123)) {
    case Success(x) => x
    case Failure(e) => 0
  }

  g {
    Future { 123 }
  } {
    case Success(x) => x
    case Failure(e) => 0
  }

  g ({
    Future { 123 }
  }) ({
    case Success(x) => x
    case Failure(e) => 0
  })

  def h: PartialFunction[Try[Int], Int] = {
    case Success(x) => x
    case Failure(e) => 0
  }

  val future = Future.successful(123)

  g(future)(h)*/
}

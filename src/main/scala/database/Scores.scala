package database

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import model.tables.Tables


object Scores {
  def apply()(implicit dc: DatabaseComponent, ec: ExecutionContext) = new Scores
}

@com.github.ghik.silencer.silent
class Scores(implicit val dc: DatabaseComponent, ec: ExecutionContext) {

  import dc.profilePG.api._

  case class ScoresColumn(id: Int, score: Int)

  @SuppressWarnings(Array("org.wartremover.warts.Nothing"))
  class Scores(tag: Tag) extends Table[(Int, Int)](tag, "scores") {
    def id = column[Int]("id", O.PrimaryKey)

    def score = column[Int]("score")

    def * = (id, score)
  }

  private[this] val db = dc.db
  private[this] val timeout = 120.seconds
  private[this] val scores = TableQuery[Scores]
  private[this] val tableName = "scores"
  val tables = Tables()

  val logger = com.typesafe.scalalogging.Logger(getClass)

}
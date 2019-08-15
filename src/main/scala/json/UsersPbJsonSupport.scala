package json

import io.circe.syntax._
import io.circe.{Decoder, Encoder, HCursor, Json}

case class Users(id: Int, name: String, sex: String, age: Int)

case class Score(id: Int, score: Int)

trait UsersPbJsonSupport {
  implicit val scoresEncoder: Encoder[Score] = (x: Score) =>
    Json.obj(
      "id" -> x.id.asJson
      , "score" -> x.score.asJson
    )

  implicit val scoresDecode: Decoder[Score] = (c: HCursor) =>
    for {
      id <- c.downField("id").as[Int]
      score <- c.downField("score").as[Int]
    } yield {
      Score(id, score)
    }
}

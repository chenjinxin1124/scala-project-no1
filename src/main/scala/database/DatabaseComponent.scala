package database
import slick.jdbc.{JdbcBackend, JdbcProfile, PostgresProfile}
import slick.jdbc.JdbcBackend.Database

trait DatabaseComponent {
  val profilePG: JdbcProfile
  val db: Database
}

object DatabaseComponent extends DatabaseComponent {
  val profilePG: PostgresProfile = slick.jdbc.PostgresProfile
  val db: JdbcBackend.Database = Database.forConfig("database.main")
}

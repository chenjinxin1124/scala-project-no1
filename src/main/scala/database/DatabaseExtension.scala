package database

import akka.actor._
import slick.jdbc
import slick.jdbc.{JdbcBackend, MySQLProfile, PostgresProfile}
import slick.jdbc.JdbcBackend.Database


object DatabaseExtension extends ExtensionId[DatabaseExtension] with ExtensionIdProvider {

 override def lookup(): DatabaseExtension.type = DatabaseExtension

 override def createExtension(system: ExtendedActorSystem) = new DatabaseExtension(system)

 override def get(system: ActorSystem): DatabaseExtension = super.get(system)
}

class DatabaseExtension(system: ExtendedActorSystem) extends Extension {
  val databaseComponent: DatabaseComponent = new DatabaseComponent {
    val profilePG: PostgresProfile = slick.jdbc.PostgresProfile
    val db: JdbcBackend.Database = Database.forConfig("database.main")
  }
}

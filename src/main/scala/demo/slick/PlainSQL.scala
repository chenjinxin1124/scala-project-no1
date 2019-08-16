package demo.slick

import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object PlainSQL extends App {
  var out = new ArrayBuffer[String]()

//  def println(s: String): Unit = out += s

  //#getresult
  // Case classes for our data
  case class Person(id: Int, name: String, age: Int)

  // Result set getters
  implicit val getPersonResult = GetResult(r => Person(r.nextInt, r.nextString, r.nextInt))
  //#getresult

  val db = Database.forConfig("mydb")

  try {
    val f: Unit = {

      val a: DBIO[Unit] = DBIO.seq(
        createPerson,
        createStudent,
        insertPerson,
        printAll,
        personByAge(13).map { s =>
          println(s"Person : $s")
        },
      )
      print("---------------------")
      Await.result(db.run(a), Duration.Inf)
    }
  } finally db.close

//  out.foreach(Console.out.println)

  //#sqlu
  def createPerson: DBIO[Int] =
    sqlu"""create table person(
      id int not null,
      name varchar not null,
      age int)"""
  def createStudent: DBIO[Int] =
    sqlu"""create table student("id" int)"""

  def insertPerson: DBIO[Unit] = DBIO.seq(
    // Insert some suppliers
    sqlu"insert into person values(1, 'A', 12)",
    sqlu"insert into person values(2, 'S',13)",
    sqlu"insert into person values(3, 'T',12)",
  )

  //#sqlu

  def printAll: DBIO[Unit] =
  // Iterate through all coffees and output them
    sql"select * from Person".as[Person].map { cs =>
      println("Person:")
      for (c <- cs)
        println("* " + c.id + "\t" + c.name + "\t" + c.age)
    }

  def personByAge(age: Int): DBIO[Seq[(String, Int)]] = {
    //#sql
    sql"""select name,age
          from person p
          where p.age < $age""".as[(String, Int)]
    //#sql
  }
}
package com.knoldus.user.impl.eventsourcing

import akka.Done
import com.datastax.driver.core.{BoundStatement, PreparedStatement}
import com.knoldus.user.api.User
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession

import scala.concurrent.{ExecutionContext, Future}

class UserRepository(session: CassandraSession)(implicit ec: ExecutionContext) {

  var userStatement: PreparedStatement = _

  def createTable(): Future[Done] = {
    session.executeCreateTable(
      """
        |CREATE TABLE IF NOT EXISTS usertable(
        |id text PRIMARY KEY,
        |name text,
        |age int
        |);
      """.stripMargin)

    session.executeCreateTable(
      """
        |CREATE INDEX IF NOT EXISTS
        |name_index ON usertable (name);
      """.stripMargin)
  }

  def createPreparedStatements: Future[Done] = {
    for{
      userPreparedStatement <- session.prepare("INSERT INTO usertable(id, name, age) VALUES (?, ?, ?)")
    } yield{
      userStatement = userPreparedStatement
      Done
    }
  }

  def storeUser(user: User): Future[List[BoundStatement]] = {
    val userBindStatement = userStatement.bind()
    userBindStatement.setString("id", user.id)
    userBindStatement.setString("name", user.name)
    userBindStatement.setInt("age", user.age)
    Future.successful(List(userBindStatement))
  }

  def getUserByName(name: String): Future[Option[User]] =
    session.selectOne(s"SELECT * FROM usertable WHERE name = '$name'").map{optRow =>
      optRow.map{row =>
        val id = row.getString("id")
        val name = row.getString("name")
        val age = row.getInt("age")
        User(id, name, age)
      }
    }

}

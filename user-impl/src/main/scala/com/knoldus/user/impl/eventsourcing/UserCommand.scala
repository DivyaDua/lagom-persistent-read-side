package com.knoldus.user.impl.eventsourcing

import akka.Done
import com.knoldus.user.api.User
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

trait UserCommand[R] extends ReplyType[R]

case class CreateUserCommand(user: User) extends UserCommand[Done]

object CreateUserCommand{
  implicit val format: Format[CreateUserCommand] = Json.format
}

case class GetUserCommand(id: String) extends UserCommand[User]

object GetUserCommand{
  implicit val format: Format[GetUserCommand] = Json.format
}

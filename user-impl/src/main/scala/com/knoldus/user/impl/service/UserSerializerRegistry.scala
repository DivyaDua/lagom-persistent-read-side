package com.knoldus.user.impl.service

import com.knoldus.user.api.User
import com.knoldus.user.impl.eventsourcing._
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

import scala.collection.immutable

object UserSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: immutable.Seq[JsonSerializer[_]] = immutable.Seq(
    JsonSerializer[User],
    JsonSerializer[CreateUserCommand],
    JsonSerializer[GetUserCommand],
    JsonSerializer[UserCreated],
    JsonSerializer[UserState]
  )
}

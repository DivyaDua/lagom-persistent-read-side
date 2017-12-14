package com.knoldus.user.impl.eventsourcing

import com.knoldus.user.api.User
import play.api.libs.json.{Format, Json}

case class UserState(user: Option[User], timeStamp: String)

object UserState{
  implicit val format: Format[UserState] = Json.format
}


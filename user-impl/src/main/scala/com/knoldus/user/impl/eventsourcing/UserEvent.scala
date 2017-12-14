package com.knoldus.user.impl.eventsourcing

import com.knoldus.user.api.User
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventShards, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

trait UserEvent extends AggregateEvent[UserEvent] {
  override def aggregateTag: AggregateEventTagger[UserEvent] = UserEvent.Tag
}

object UserEvent {
  val numberOfShards = 4
  val Tag: AggregateEventShards[UserEvent] = AggregateEventTag.sharded[UserEvent](numberOfShards)
}

case class UserCreated(user: User) extends UserEvent

object UserCreated{
  implicit val format: Format[UserCreated] = Json.format
}

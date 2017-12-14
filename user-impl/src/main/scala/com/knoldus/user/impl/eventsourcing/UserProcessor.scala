package com.knoldus.user.impl.eventsourcing

import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, ReadSideProcessor}

import scala.concurrent.ExecutionContext

class UserProcessor(session: CassandraSession, readSide: CassandraReadSide)(implicit ec: ExecutionContext)
  extends ReadSideProcessor[UserEvent] {

  val userRepository = new UserRepository(session)

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[UserEvent] = {
    readSide.builder[UserEvent]("userEventOffset")
      .setGlobalPrepare(userRepository.createTable)
      .setPrepare(_ => userRepository.createPreparedStatements)
      .setEventHandler[UserCreated](e ⇒ userRepository.storeUser(e.event.user))
      .build()
  }

  override def aggregateTags: Set[AggregateEventTag[UserEvent]] =
    UserEvent.Tag.allTags
}

package com.knoldus.user.impl.eventsourcing

import java.time.LocalDateTime

import akka.Done
import com.knoldus.user.api.User
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

class UserEntity extends PersistentEntity {

  override type Command = UserCommand[_]
  override type Event = UserEvent
  override type State = UserState

  override def initialState = UserState(None, LocalDateTime.now().toString)

  override def behavior: (UserState) => Actions = {
    case UserState(_, _) => Actions()
      .onCommand[CreateUserCommand, Done] {
      case (CreateUserCommand(user), ctx, _) ⇒
        ctx.thenPersist(UserCreated(user))(_ ⇒ ctx.reply(Done))
    }
      .onReadOnlyCommand[GetUserCommand, User] {
       case (GetUserCommand(id), ctx, state) =>
        ctx.reply(state.user.getOrElse(User(id, "not found", -1)))
    }
      .onEvent {
        case (UserCreated(user), _) ⇒
          UserState(Some(user), LocalDateTime.now().toString)
      }
  }

}

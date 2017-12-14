package com.knoldus.user.impl.service

import akka.{Done, NotUsed}
import com.knoldus.user.api.{User, UserService}
import com.knoldus.user.impl.eventsourcing._
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.{PersistentEntityRef, PersistentEntityRegistry}

import scala.concurrent.ExecutionContext

class UserServiceImpl(persistentEntityRegistry: PersistentEntityRegistry, userRepository: UserRepository)
                     (implicit ec: ExecutionContext) extends UserService {

  override def createUser(id: String, name: String, age: Int): ServiceCall[NotUsed, String] = {
    ServiceCall { _ =>
      val user = User(id, name, age)
      ref(id).ask(CreateUserCommand(user)).map {
        case Done => s"Hello ${user.name}! Your account has been created."
      }
    }
  }

  override def getUserById(id: String): ServiceCall[NotUsed, String] =
    ServiceCall { _ =>
      ref(id).ask(GetUserCommand(id)).map(user =>
        s"User for id:$id is ${user.name}")
    }

  override def getUserByName(name: String): ServiceCall[NotUsed, String] =
    ServiceCall { _ =>
    userRepository.getUserByName(name).map(user =>
      s"User for name:$name has id: ${user.get.id} and age: ${user.get.age}"
    )
  }

  def ref(id: String): PersistentEntityRef[UserCommand[_]] = {
    persistentEntityRegistry
      .refFor[UserEntity](id)
  }

}

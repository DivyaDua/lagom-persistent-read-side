package com.knoldus.user.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait UserService extends Service {

  def createUser(id: String, name: String, age: Int): ServiceCall[NotUsed, String]
  def getUserById(id: String): ServiceCall[NotUsed, String]
  def getUserByName(name: String): ServiceCall[NotUsed, String]

  override def descriptor: Descriptor = {
    import Service._

    named("user_service")
      .withCalls(
        restCall(Method.POST, "/user/create/:id/:name/:age", createUser _),
        restCall(Method.GET, "/user/details/id/:id", getUserById _),
        restCall(Method.GET, "/user/details/name/:name", getUserByName _)
      ).withAutoAcl(true)
  }
}

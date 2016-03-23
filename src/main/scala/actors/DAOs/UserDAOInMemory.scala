package actors.DAOs

import actors.UserProtocol.User
import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable


class UserDAOInMemory extends Actor with ActorLogging {
  val users: scala.collection.mutable.MutableList[User] = mutable.MutableList()
  def receive: Receive = ???
}

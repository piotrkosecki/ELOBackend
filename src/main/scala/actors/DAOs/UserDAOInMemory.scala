package actors.DAOs

import actors.UserProtocol.{AddUser, GetUserInfo, User}
import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable


class UserDAOInMemory extends Actor with ActorLogging {
  val users: scala.collection.mutable.MutableList[User] = mutable.MutableList()
  def receive: Receive = {
    case AddUser(user) => {
      log.info(s"Added user $user")
      users+=user
      sender ! user
    }
    case GetUserInfo(id) => {
      sender ! users.find(_.id == id)
    }
  }
}

package actors

import actors.DAOs.{UserDAOInMemory, UserDAOPersistent}
import actors.UserProtocol._
import akka.actor._
import akka.pattern._
import com.typesafe.config.ConfigFactory

import scala.util.Success

class UserActor extends Actor with ActorLogging with ActorImplicits {

  val config = ConfigFactory.load()

  def receive: Receive = {
    case GetUserInfo(id) => (getUserDAO ? GetUserInfo(id)).mapTo[Option[User]].pipeTo(sender)
    case AddUser(user) => {
      (getUserDAO ? AddUser(user)).mapTo[Boolean].pipeTo(sender)
    }
  }

  val getUserDAO: ActorRef = {
    if(config.getBoolean("application.inMemory")) {
      system.actorOf(Props[UserDAOInMemory])
    }
    else {
      system.actorOf(Props[UserDAOPersistent])
    }
  }
}

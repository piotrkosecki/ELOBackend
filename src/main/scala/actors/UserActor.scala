package actors

import actors.DAOs.{UserDAOInMemory, UserDAOPersistent}
import actors.UserProtocol._
import akka.actor._
import akka.pattern.ask
import com.typesafe.config.ConfigFactory

class UserActor extends Actor with ActorLogging with ActorImplicits {

  val config = ConfigFactory.load()

  def receive: Receive = {
    case GetUserInfo(id) => (getUserDAO ? GetUserInfo(id)).mapTo[User]
    case AddUser(user) =>  (getUserDAO ? AddUser(user)).mapTo[Boolean]
  }

  def getUserDAO: ActorRef = {
    if(config.getBoolean("application.inMemory")) {
      system.actorOf(Props[UserDAOInMemory])
    }
    else {
      system.actorOf(Props[UserDAOPersistent])
    }
  }
}

package actors

import actors.DAOs.{UserDAOInMemory, UserDAOPersistent}
import actors.UserProtocol._
import akka.actor._
import akka.pattern._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

class UserActor extends Actor with ActorLogging {
  implicit lazy val timeout: Timeout = 5 seconds
  implicit lazy val system = ActorSystem("UserActor")
  implicit lazy val executor = system.dispatcher
  implicit lazy val materializer = ActorMaterializer()
  val config = ConfigFactory.load()

  def receive: Receive = {
    case GetUserInfo(id) => (userDAOActor ? GetUserInfo(id)).mapTo[Option[User]].pipeTo(sender)
    case AddUser(user) => {
      (userDAOActor ? AddUser(user)).mapTo[User].pipeTo(sender)
    }
  }

  val userDAOActor: ActorRef = {
    if(config.getBoolean("application.inMemory")) {
      context.actorOf(Props[UserDAOInMemory])
    }
    else {
      context.actorOf(Props[UserDAOPersistent])
    }
  }
}

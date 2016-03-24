package actors

import actors.DAOs.{UserDAOInMemory, UserDAOPersistent}
import actors.UserProtocol._
import akka.actor.Actor.Receive
import akka.actor._
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContextExecutor

class UserActor extends Actor with ActorLogging {

  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer
  implicit val timeout: Timeout
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

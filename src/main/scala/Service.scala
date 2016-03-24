import actors.UserActor
import actors.UserProtocol.{AddUser, GetUserInfo, User}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import akka.pattern.ask
import akka.util.Timeout
import play.api.libs.json.Json

import scala.concurrent.ExecutionContextExecutor

trait Service {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer
  implicit val timeout: Timeout

  val userActor = system.actorOf(Props[UserActor])

  case class UserCreation(login: String)

  val routes = {
    logRequestResult("elo-api") {
      pathPrefix("user") {
        (get & path(Segment)) { id =>
          complete {
            (userActor ? GetUserInfo(id)).mapTo[User]
          }
        }
      } ~
        (post & entity(as[UserCreation])) { user =>
          complete {
            userActor ? AddUser(user.login)
          }
        }
    }
  }
}

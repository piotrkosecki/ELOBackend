import actors.UserActor
import actors.UserProtocol.{AddUser, GetUserInfo, User}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.Materializer
import akka.util.Timeout
import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.concurrent.ExecutionContextExecutor

trait Service extends Protocols with LazyLogging {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer
  implicit val timeout: Timeout

  val userActor = system.actorOf(Props[UserActor])


  val routes = {
    logRequestResult("elo-api") {
      pathPrefix("user") {
        (get & path(Segment)) { id =>
          complete {
            logger.info(s"GET /user/$id")
            val user = (userActor ? GetUserInfo(id)).mapTo[User]
            user.map(_.toString)
          }
        }
      } ~
        (post & entity(as[String])) { userLogin =>
          complete {
            logger.info(s"POST /user/$userLogin")
            val result = (userActor ? AddUser(User(userLogin))).mapTo[Boolean]
            result.map(_.toString)
          }
        }
    }
  }

}

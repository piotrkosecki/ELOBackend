import actors.UserProtocol.{AddUser, GetUserInfo, User}
import actors.UserActor
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.scalalogging.slf4j.LazyLogging
import fommil.sjs.FamilyFormats._

import scala.concurrent.ExecutionContextExecutor

trait Service extends Protocols with LazyLogging {

  implicit val system: ActorSystem
  implicit val executor: ExecutionContextExecutor
  implicit val materializer: ActorMaterializer
  implicit val timeout: Timeout

  lazy val userActor = system.actorOf(Props[UserActor], "userActor")


  val routes = {
    logRequestResult("elo-api") {
      pathPrefix("user") {
        (get & path(Segment)) { id =>
          complete {

            val user = (userActor ? GetUserInfo(id)).mapTo[Option[User]]
            user.map(_.toString)
          }
        }
      } ~
        (post & entity(as[String])) { userLogin =>
          complete {

            val result = (userActor ? AddUser(User(userLogin))).mapTo[Boolean]
            result.map(_.toString)
          }
        }
    }
  }

}

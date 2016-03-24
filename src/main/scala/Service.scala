import actors.UserProtocol.{AddUser, GetUserInfo, User}
import actors.{ActorImplicits, UserActor}
import akka.actor.Props
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import com.typesafe.scalalogging.slf4j.LazyLogging

trait Service extends Protocols with LazyLogging with ActorImplicits {

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

import java.time.LocalDateTime

import actors.UserProtocol.{User, UserStatus}
import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer

import scala.collection._
import scala.concurrent.ExecutionContextExecutor

trait Service {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer


  val routes = {
    logRequestResult("elo-api") {
      pathPrefix("user") {
        (get & entity(as[String])) { id =>
          complete {

          }
        }
      } ~
        (post & entity(as[User])) { create =>
          complete {
            ???
          }
        }
    }
  }
}

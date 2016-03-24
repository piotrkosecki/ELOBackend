package actors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.duration._


trait ActorImplicits {
  implicit lazy val system = ActorSystem("ELOApp")
  implicit lazy val executor = system.dispatcher
  implicit lazy val materializer = ActorMaterializer()
  implicit lazy val timeout: Timeout = 5 seconds
}

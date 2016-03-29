
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.LazyLogging
import scala.concurrent.duration._

import scala.util.{Failure, Success}

object ELOApp extends App with Service with LazyLogging {

  override implicit lazy val system = ActorSystem("ELOApp")
  override implicit lazy val executor = system.dispatcher
  override implicit lazy val materializer = ActorMaterializer()
  override implicit lazy val timeout: Timeout = 5 seconds

  val config = ConfigFactory.load()

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port")).onComplete {
    case Success(binding) => logger.info(s"Server ready at: ${binding.localAddress}")
    case Failure(err)     =>
      logger.error("Failed to start the server.", err)
      system.terminate().onComplete {
        case _ => logger.info("Terminated.")
      }
  }

}
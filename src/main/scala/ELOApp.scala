
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.util.{Failure, Success}

object ELOApp extends App with Service with LazyLogging {
  override implicit val system = ActorSystem("ELOApp")
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()
  override implicit val timeout = 5 seconds

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

import actors.ActorImplicits
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.util.{Failure, Success}

object ELOApp extends App with Service with LazyLogging with ActorImplicits {


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
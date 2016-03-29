import actors.UserProtocol._
import spray.json.DefaultJsonProtocol
import fommil.sjs.FamilyFormats._


trait Protocols extends DefaultJsonProtocol {
  implicit val userStatusFormat = jsonFormat1(InGame.apply)
  implicit val userCreationFormat = jsonFormat1(UserCreation.apply)
  implicit val idleFormat = jsonFormat0(Idle.apply)
  implicit val inQueueFormat = jsonFormat0(InQueue.apply)
  implicit val inGameFormat = jsonFormat1(InGame.apply)
  implicit val userFormat = jsonFormat4(User.apply)
}
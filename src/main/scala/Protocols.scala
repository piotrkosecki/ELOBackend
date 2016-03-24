import actors.UserProtocol._
import spray.json.DefaultJsonProtocol

trait Protocols extends DefaultJsonProtocol {
  //implicit val userFormat = jsonFormat4(User.apply)
  implicit val UserStatusFormat = jsonFormat1(InGame.apply)
 // implicit val matchHistory = jsonFormat3(IpPairSummary.apply)
 implicit val userCreationFormat = jsonFormat1(UserCreation.apply)
}
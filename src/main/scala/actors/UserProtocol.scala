package actors

import java.time.LocalDateTime
import java.util.UUID

import play.api.libs.json.Json


object UserProtocol {
  case class AddUser(user: User)
  case class GetUserInfo(id: String)
  case class UpdateUserInfo(id: String, rank: Int)
  case class UpdateUserStatus(id: String, status: UserStatus)

  sealed trait UserStatus
  case object Idle extends UserStatus
  case object InQueue extends UserStatus
  case class InGame(id: String) extends UserStatus

  case class User(login: String, rating: Int = 1000, status: UserStatus = Idle, id: String = UUID.randomUUID().toString)
  case class MatchHistory(date: LocalDateTime, players: List[User], won: Option[User])

  implicit val userFormat = Json.format[User]

  implicit val matchHistoryFormat = Json.format[MatchHistory]

  implicit val userStatusFormat = Json.format[UserStatus]

}

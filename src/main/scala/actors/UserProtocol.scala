package actors

import java.time.LocalDateTime
import java.util.UUID



object UserProtocol {

  case class AddUser(user: User)
  case class GetUserInfo(id: String)
  case class UpdateUserInfo(id: String, rank: Int)
  case class UpdateUserStatus(id: String, status: UserStatus)

  sealed trait UserStatus
  case class Idle() extends UserStatus
  case class InQueue() extends UserStatus
  case class InGame(id: String) extends UserStatus

  case class User(login: String, rating: Int = 1000, status: UserStatus = Idle(), id: String = UUID.randomUUID().toString)
  case class MatchHistory(date: LocalDateTime, players: List[User], won: Option[User])

  case class UserCreation(login: String)

}

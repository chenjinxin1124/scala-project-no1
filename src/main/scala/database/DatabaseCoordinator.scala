package database

trait DatabaseCoordinator {
  implicit val dc: DatabaseComponent
}

package GameControllers

import org.scalatest._
import GameControllers._

class HelloSpec extends FlatSpec with Matchers {
  "Adding Player Test with name \"Player#1\"" should "say playerName" in {
    var newPlayer = new NormalPlayer("Player#1")
    newPlayer.getName shouldEqual "Player#1"
  }
}

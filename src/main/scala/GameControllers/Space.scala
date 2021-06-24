package GameControllers
import GameControllers.{Player}

abstract class Space extends ocupaitPlayer
case object WinSpace extends Space 
case class NormalSpace() extends Space 
case class BridgeSpace() extends Space 
case class GooseSpace() extends Space 

trait ocupaitPlayer{
	private var currentPlayer: Player = EmptyPlayer
	def getPlayer(): Player = this.currentPlayer
	def setPlayer(player: Player){
		player match {
			case EmptyPlayer =>{}
			case player @ NormalPlayer(name)=>{
				this.currentPlayer = player
			}
		}
	}
	def isOccupate():Boolean = this.currentPlayer != EmptyPlayer
}
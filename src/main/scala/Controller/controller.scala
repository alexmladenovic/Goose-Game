package Controller
import GameControllers.{Board}

object controller{
	private var started = false
	private var returnString = ""
	private var auto = false
	private var hasWinner = false

	private def Concat2returnString(value:String){
		this.returnString = this.returnString.concat(value)
	}

	def endGame(){this.hasWinner = true}

	def getHasWinner():Boolean = this.hasWinner

	def setHasWinner(){this.hasWinner = true}

	def setAuto(value:Boolean){this.auto = value}

	def hasGameStarted(): Boolean = this.started

	def startGame(){
		this.started = true
		Board.initializeBorad(this.auto)
		Board.startGame
		if(auto){
			this.movePlayerAutomatic
		}
	}

	def resetReturnString(){this.returnString = ""}

	def addPlayer(name:String): String = {
		this.resetReturnString()
		if(this.hasGameStarted){
			Concat2returnString("The game has started you can't add players\n")		
			Concat2returnString("You can only move the players to see how enter <help>\n")	
			return this.returnString
		}else{
			var returnVal = Board.addPlayer(name)
			Concat2returnString(returnVal)	
			return this.returnString	
		}
	}

	def movePlayer(name:String):String = {
		if(this.hasGameStarted){
			var position1 = Board.GetRandomNumber //get a random value form 1~6
			var position2 = Board.GetRandomNumber
			return this.movePlayer(name, position1, position2)
		}else{
			return "You have to start the game first you can see how by typing <help>"
		}
	}

	def movePlayer(name:String, pos1:Int, pos2:Int):String = {
		if(this.hasGameStarted)
			return Board.movePlayer(name,pos1,pos2)
		else
			return "You have to start the game first you can see how by typing <help>"
	}	

	private def movePlayerAutomatic(){
		while(!Board.hasWinner){
			Thread.sleep(1000)
			//Get PLayer info current
			var name = Board.getCurrentPlayerName
			var pos1 = Board.GetRandomNumber
			var pos2 = Board.GetRandomNumber

			//move player
			var returnString = movePlayer(name, pos1, pos2)
			println(returnString)
			//Change PLayer Turn
			Board.nextTurn
		}
		this.endGame
	}
}
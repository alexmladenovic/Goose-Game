package GameControllers
import GameControllers._

object Board{
	private var players = List[NormalPlayer]()
	private var start = false
	private val winPosition = 63
	private var haveWinner = false
	private var bounceCurPlayer = false
	private var WinnerName = "No Winer"
	private var Goose = false
	private var GooseString = ""
	private var Bridge = false
	private var startingPos = 0
	private var intermidatePos = 0
	private var occupateString = ""
	private var occupate = false
	private var spaces = new Array[Space](64)
	private var auto = false
	private var CurrentePlayer: NormalPlayer = new NormalPlayer("EmptyPlayer")
	private var returnString = ""


	private def Concat2returnString(value:String){
		this.returnString = this.returnString.concat(value)
	}

	def GetRandomNumber(): Int = Dice.getValue

	private def changeGoose(value:Boolean){
		this.Goose = true
	}

	def getCurrentPlayerLastPosition(): Int = this.CurrentePlayer.getLastPosition
	def getCurrentPlayerPosition(): Int = this.CurrentePlayer.getPosition
	def getCurrentPlayerName(): String =  this.CurrentePlayer.getName
	
	def nextTurn(){
		Turn.nextTurn
		this.CurrentePlayer = players(Turn.getCurrentPlayer)
	}

	def resetFields(){
		this.returnString = ""
		this.Bridge = false
	}

	def getWinPosition():Int = this.winPosition

	def startGame {
		this.start = true
		Turn.addPlayers(players.length)
	}

	def getStart(): Boolean = return this.start
	def hasWinner(): Boolean = return this.haveWinner
	def getBounce():Boolean = return this.bounceCurPlayer
	def changeBounce(){this.bounceCurPlayer = false}
		
	def initializeBorad(auto:Boolean){
		if(auto){
			this.auto = true
			this.CurrentePlayer = players(0)	
		}
		for(i <- 0 to 63){
			if(i == 6)
				this.spaces(i) = new BridgeSpace()
			else if((i == 5) || (i==9) || (i==14) || (i==18) || (i==23) || (i==27) )
				this.spaces(i) = new GooseSpace()
			else if(i == this.getWinPosition)
				this.spaces(i) = WinSpace
			else
				this.spaces(i) = new NormalSpace()
			this.spaces(i).setPlayer(EmptyPlayer)
		}
	}

	def occupateSpace(player:Player, space:Space){
		var oldPlayer = space.getPlayer
		oldPlayer match {
			case EmptyPlayer => {
				space.setPlayer(player)
			}
			case oldPlayer @ NormalPlayer(name) => {
				this.occupate = true

				spaces(player.getLastPosition) match {
					case WinSpace =>{ }
					case NormalSpace () => {
						oldPlayer.move2Possition(player.getLastPosition)
					}
					case BridgeSpace() =>{
						oldPlayer.move2Possition(player.getLastPosition-1)
					}
					case GooseSpace() =>{
						oldPlayer.move2Possition(player.getLastPosition-1)
					}
				}
				space.setPlayer(player)
				spaces(oldPlayer.getPosition).setPlayer(oldPlayer)

				this.Concat2returnString(" On "+player.getPosition+" there is "+oldPlayer.getName+" who returns to "+oldPlayer.getPosition)
			}
		}
	}

	def SpaceFunction(tempSpace: Space, diceValue:Int, player:NormalPlayer): Int = tempSpace match {
			case WinSpace => { 
				this.WinnerName = player.getName
				this.haveWinner = true
				0
			}
			case NormalSpace() => {
				this.occupateSpace(player, tempSpace)
				0
			}
			case BridgeSpace() => {
				this.Bridge = true
				//this.occupateSpace(player, tempSpace)
				player.move2Possition(12)
				0
			}
			case GooseSpace() => {
				//this.occupateSpace(player, tempSpace)
				changeGoose(true)
				this.Concat2returnString(", The Goose. "+player.getName+" moves again and gose to "+(player.getPosition+diceValue))
				diceValue
			}
	}
	

	def movePlayer(name:String, pos1: Int, pos2: Int):String = {
		this.returnString = ""
		if(pos1 <=0 || pos1>6) return "Error first dick Wrong Number"
		if(pos2 <=0 || pos2>6) return "Error second dick Wrong Number"

		val player = players.filter(_.getName.equals(name)).toList(0) //Only one will exist because we check for duplicates when we add it to the List
		
		if(auto == false){
			this.CurrentePlayer = player
		}

		val DiceSum = pos1+pos2
		
		startingPos = player.getPosition
		player.movePossition(DiceSum)
		intermidatePos = player.getPosition
		
		this.Concat2returnString(player.getName + " rolls "+pos1+", "+pos2+". "+player.getName+" moves from "+startingPos+" to "+intermidatePos+".")
		
		BounceCheck(player)

		//The bounce functionality make it a function
		var spaceNumber = 1;
		do{
			this.Bridge = false
			spaceNumber = SpaceFunction(spaces(player.getPosition), DiceSum, player)
			player.movePossition(spaceNumber)
		}while(spaceNumber != 0 || this.Bridge);
		BounceCheck(player)
		

		if(this.haveWinner){
			this.Concat2returnString(" "+player.getName+" Wins!!")
		}

		return this.returnString
	}

	def BounceCheck(player:Player){
		player match{
			case EmptyPlayer =>{}
			case player @ NormalPlayer(v) => {
				if(player.getPosition > 63){
					var lastPosition = player.getLastPosition
					this.bounceCurPlayer = true
					player.move2Possition(this.winPosition - (player.getPosition - this.winPosition))
					player.setLastPosition(lastPosition)
					this.Concat2returnString(" "+player.getName+" bounces! "+player.getName+" returned to "+player.getPosition)
				}
			}
		}
	}

	def addPlayer(name: String): String = {
		this.returnString = ""
		if(this.checkDuplicate(name)){
			this.Concat2returnString(name+": already existing player")
			return this.returnString
		}
		var newPlayer = new NormalPlayer(name)
		this.players = this.players ::: List(newPlayer)
		this.printPlayer()
		return this.returnString
	}

	def checkDuplicate(name: String): Boolean = return players.count(_.getName.equals(name)) == 1
	

	private def printPlayer(){
		this.Concat2returnString("players: ")
		for(player<-players){
			this.Concat2returnString(" "+player.getName)
			val lastPlayer = players(players.length-1) 	
			if(!player.getName.equals(lastPlayer.getName)){
				this.Concat2returnString(",")
			}
		}
	}
}
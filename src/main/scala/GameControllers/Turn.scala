package GameControllers

object Turn{
	private var currentPlayer = 0
	private var totalPlayers = 0


	def getCurrentPlayer(): Int = this.currentPlayer

	def nextTurn(){
		if(this.currentPlayer == totalPlayers-1){
			this.currentPlayer = 0
		}else{
			this.currentPlayer += 1
		}
	}

	def addPlayers(num:Int){
		this.totalPlayers = num
	}
}
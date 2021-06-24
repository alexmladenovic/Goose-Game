package GameControllers

trait Position{
	private var position = 0
	private var lastPosition = 0

	def getLastPosition():Int = this.lastPosition
	def movePossition(num:Int){
		if(num != 0) this.lastPosition = this.position
		this.position += num
	}
	def getPosition(): Int = {
		this.position
	}
	def move2Possition(num: Int){
		this.lastPosition = this.position
		this.position = num
	}
	def setLastPosition(num:Int){
		this.lastPosition = num
	}
}
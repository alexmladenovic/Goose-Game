package GameControllers

abstract class Player extends Position
case object EmptyPlayer extends Player
case class NormalPlayer(private var name:String) extends Player{
	this.setName(name)
	def this(){
		this("No Name")
	}

	def getName(): String = name

	private def setName(name: String){
		if(name.isEmpty) this.name = "No Name"
		else if(name(0).isDigit) this.name = "No Name"
		else this.name = name
	}

	override def toString(): String = return "Name %s".format(this.name)
}
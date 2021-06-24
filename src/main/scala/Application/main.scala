package Application
import Controller._

object main extends App {

	override def main(args: Array[String]){
  	startGame()
	}

	def startGame(){
		printf("Do you want to game to be automatic after you entered the players[y/n]: ")
		val auto = readLine

		controller.setAuto(auto.equals("y"))

		printOptions()
		
		while(!controller.getHasWinner){
			printf(">")
			val input = readLine

			if(input.equals("start")){
					controller.startGame
			}else if(input.equals("help"))
				printOptions()
			else if(input.equals("end"))
					controller.endGame
			else if(input contains "add player"){
				try{
					val name = input.split(" ")(2)
					var returnString = controller.addPlayer(name)
					println(returnString)
				}catch{
					case e: Any => println("You commands you entered is not correct")
				}
			}else if( (input contains "move")){ //Both move commands are implemented here
	
				var position1 = 0
				var position2 = 0

				try{
					var returnString = "" 
					val name = input.split(" ")(1)
					if(input contains ","){
						position1 = input.split(" ")(2).split(",")(0).toInt
						position2 = input.split(" ")(3).toInt
						returnString = controller.movePlayer(name, position1, position2)
					}else
						returnString = controller.movePlayer(name)

					println(returnString)
				}catch{
						case e: Any => println("The command you entered is not correct")
				}
			}		
			else{
				printf("Invalid option try something else\n")
				printOptions()
			}
		}
	}

	def printOptions(){
		printf("help - to show the help menu\n")
		printf("start - to start the game, after you can't add players\n")
		printf("add player <name> - to add a player with the name\n")
		printf("move name x, y - move a player x+y positions\n")
		printf("end - to end the game\n")
		
	}
}
package GameControllers
import scala.util.Random

object Dice{
	def getValue(): Int = Random.nextInt(6)+1
}
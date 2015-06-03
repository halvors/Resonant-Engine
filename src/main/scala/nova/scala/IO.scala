package nova.scala

import nova.core.block.Block
import nova.core.component.Component
import nova.core.event.{Event, EventBus}
import nova.core.network.{PacketHandler, Sync}
import nova.core.retention.{Storable, Stored}
import nova.core.util.Direction

/**
 * A Trait that handles input and outputs
 *
 * @author Calclavia
 */
//TODO: Would this be useful for NOVA?
class IO(block: Block) extends Component with Storable with PacketHandler {

	/**
	 * IO METHODS.
	 * Default: Connect from all sides. "111111"
	 * Output all sides: 728
	 * 0 - Nothing
	 * 1 - Input
	 * 2 - Output
	 */
	//TODO: Using ternary is kind of ineffective
	@Stored
	@Sync
	var mask = 364

	var changeEvent = new EventBus[Event]
	/*
		def toggleIO(side: Int, entityPlayer: EntityPlayer): Boolean = {
			if (Game.network.isServer) {
				val newIO = (getIO(Direction.fromOrdinal(side)) + 1) % 3
				setIO(Direction.fromOrdinal(side), newIO)
				entityPlayer.addChatMessage(new ChatComponentText("Side changed to: " + (if (newIO == 0) "None" else if (newIO == 1) "Input" else "Output")))
				block.world.markChange(block.transform.position)
			}
			return true
		}

		def setIO(dir: Direction, ioType: Int) {
			val currentIO: String = getIOMapBase3
			val str: StringBuilder = new StringBuilder(currentIO)
			str.setCharAt(dir.ordinal, Integer.toString(ioType).charAt(0))
			val prevMask = mask
			mask = Integer.parseInt(str.toString, 3)
			if (mask != prevMask) {
				changeEvent.publish(new Event)
			}
		}*/

	def getIO(dir: Direction): Int = {
		val currentIO: String = getIOMapBase3
		return Integer.parseInt("" + currentIO.charAt(dir.ordinal))
	}

	def getIOMapBase3: String = {
		var currentIO: String = Integer.toString(mask, 3)
		while (currentIO.length < 6) {
			currentIO = "0" + currentIO
		}
		return currentIO
	}

	/**
	 * The input directions.
	 */
	def inputMask = Direction.DIRECTIONS.filter(getIO(_) == 1).map(1 << _.ordinal()).foldLeft(0)((a, b) => a | b)

	/**
	 * The output directions.
	 */
	def outputMask = Direction.DIRECTIONS.filter(getIO(_) == 2).map(1 << _.ordinal()).foldLeft(0)((a, b) => a | b)

}
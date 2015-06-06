package nova.scala

import nova.core.block.Block
import nova.core.component.Component
import nova.core.component.transform.Orientation
import nova.core.event.{Event, EventBus}
import nova.core.network.{Syncable, Sync}
import nova.core.retention.{Storable, Stored}
import nova.core.util.Direction

/**
 * A Trait that handles input and outputs
 *
 * @author Calclavia
 */
//TODO: Would this be useful for NOVA?
class IO(block: Block) extends Component with Storable with Syncable {

	@Stored
	@Sync
	var inputMask = 0x3F

	@Stored
	@Sync
	var outputMask = 0x00

	var changeEvent = new EventBus[Event]

	def getIO(dir: Direction): Int = {
		if ((inputMask & (1 << dir.ordinal())) != 0) {
			return 1
		}
		if ((outputMask & (1 << dir.ordinal())) != 0) {
			return 2
		}
		return 0
	}

	/**
	 * Helper method
	 */
	def setIOAlternatingOrientation() {
		val dirMask = 1 << block.get(classOf[Orientation]).orientation.ordinal
		val positiveMask = 0x2A
		val isPositive = (dirMask & positiveMask) != 0

		if (isPositive) {
			inputMask = positiveMask & ~dirMask
			outputMask = inputMask >> 1
		} else {
			val negativeMask = 0x15
			outputMask = negativeMask & ~dirMask
			inputMask = outputMask << 1
		}
	}
}
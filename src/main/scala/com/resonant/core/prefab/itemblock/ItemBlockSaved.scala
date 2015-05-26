package com.resonant.core.prefab.itemblock

import java.util.Optional

import nova.core.block.{Block, BlockFactory}
import nova.core.entity.Entity
import nova.core.retention.{Data, Storable}
import nova.core.util.transform.vector.Vector3i
import nova.core.world.World


/**
 * An ItemBlock that can store its block's internal data even after the block breaks.
 *
 * @author Calclavia
 */
class ItemBlockSaved(blockFactory: BlockFactory) extends ItemBlockTooltip(blockFactory) with Storable {

	var data: Data = new Data

	override def getMaxCount: Int = 1

	override def save(data: Data) {
		data.clear()
		data.putAll(this.data)
	}

	override def load(data: Data): Unit = this.data = data

	override protected def onPostPlace(world: World, placePos: Vector3i, entity: Entity): Boolean = {
		val placedBlock: Optional[Block] = world.getBlock(placePos)

		if (placedBlock.isPresent && placedBlock.get().isInstanceOf[Storable]) {
			//Check if basic NBT data such as x,y,z is retained.
			placedBlock.get().asInstanceOf[Storable].load(data)
		}

		return super.onPostPlace(world, placePos, entity)
	}
}
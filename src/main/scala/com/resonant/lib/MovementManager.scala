package com.resonant.lib

import nova.core.block.Block
import nova.core.retention.Data
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import nova.core.world.World

/**
 * @author Calclavia
 */
abstract class MovementManager {

	/**
	 * Sets a block in a sneaky manner, without notifying any systems.
	 * @param world
	 * @param pos
	 * @param data Optionally, data can be injected into the block.
	 */
	def setSneaky(world: World, pos: Vector3D, block: Block, data: Data = null)
}

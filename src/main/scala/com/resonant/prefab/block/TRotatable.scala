package com.resonant.prefab.block

import nova.core.block.Block
import nova.core.entity.Entity
import nova.core.network.Sync
import nova.core.util.Direction
import nova.core.util.components.Stored
import nova.core.util.transform.Vector3d

trait TRotatable extends Block {

	var rotationMask = 0x3C
	var isFlipPlacement = false

	@Sync
	@Stored
	var direction = Direction.UNKNOWN

	override def getDirection: Direction = Direction.fromOrdinal(getBlockMetadata)

	def determineRotation(entity: Entity): Direction = {
		if (Math.abs(entity.getPosition.x - x) < 2 && Math.abs(entity.getPosition.z - z) < 2) {
			val d0 = entity.getPosition.y + 1.82D //- entity.yOffset

			if (canRotate(1) && d0 - y > 2.0D) {
				return Direction.UP
			}
			if (canRotate(0) && y - d0 > 0.0D) {
				return Direction.DOWN
			}
		}

		val playerSide = Math.floor(entity.getRotation.toEuler.x * 4.0F / 360.0F + 0.5D).toInt & 3
		val returnSide = if (playerSide == 0 && canRotate(2)) 2 else if (playerSide == 1 && canRotate(5)) 5 else if (playerSide == 2 && canRotate(3)) 3 else if (playerSide == 3 && canRotate(4)) 4 else 0

		if (isFlipPlacement) {
			return Direction.fromOrdinal(returnSide).opposite()
		}

		return Direction.fromOrdinal(returnSide)
	}

	def determineRotation(entity: Entity): Direction =
	{
		if (Math.abs(entity.getPosition.x - x) < 2 && Math.abs(entity.getPosition.z - z) < 2) {
			val d0 = entity.getPosition.y + 1.82D //- entity.yOffset

			if (canRotate(1) && d0 - y > 2.0D) {
				return Direction.UP
			}
			if (canRotate(0) && y - d0 > 0.0D) {
				return Direction.DOWN
			}
		}

		val playerSide = Math.floor(entity.getRotation.toEuler.x * 4.0F / 360.0F + 0.5D).toInt & 3
		val returnSide = if (playerSide == 0 && canRotate(2)) 2 else if (playerSide == 1 && canRotate(5)) 5 else if (playerSide == 2 && canRotate(3)) 3 else if (playerSide == 3 && canRotate(4)) 4 else 0

		if (isFlipPlacement) {
			return Direction.fromOrdinal(returnSide).getOpposite
		}

		return Direction.fromOrdinal(returnSide)
	}

	/**
	 * Rotatable Block
	 */
	def rotate(side: Int, hit: Vector3d): Boolean = {
		val result = getSideToRotate(side.asInstanceOf[Byte], hit.x, hit.y, hit.z)

		if (result != -1) {
			setDirection(Direction.fromOrdinal(result))
			return true
		}

		return false
	}

	override def setDirection(direction: Direction) = world.setBlockMetadataWithNotify(x, y, z, direction.ordinal, 3)

	/**
	 * Determines the side to rotate based on the hit vector on the block.
	 */
	def getSideToRotate(hitSide: Int, hit: Vector3d): Int = {
		val tBack: Int = hitSide ^ 1

		hitSide match {
			case 0 =>
			case 1 =>
				if (hit.x < 0.25) {
					if (hit.z < 0.25) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (hit.z > 0.75) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (canRotate(4)) {
						return 4
					}
				}
				if (hit.x > 0.75) {
					if (hit.z < 0.25) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (hit.z > 0.75) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (canRotate(5)) {
						return 5
					}
				}
				if (hit.z < 0.25) {
					if (canRotate(2)) {
						return 2
					}
				}
				if (hit.z > 0.75) {
					if (canRotate(3)) {
						return 3
					}
				}
				if (canRotate(hitSide)) {
					return hitSide
				}
			case 2 =>
			case 3 =>
				if (hit.x < 0.25) {
					if (hit.y < 0.25) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (hit.y > 0.75) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (canRotate(4)) {
						return 4
					}
				}
				if (hit.x > 0.75) {
					if (hit.y < 0.25) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (hit.y > 0.75) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (canRotate(5)) {
						return 5
					}
				}
				if (hit.y < 0.25) {
					if (canRotate(0)) {
						return 0
					}
				}
				if (hit.y > 0.75) {
					if (canRotate(1)) {
						return 1
					}
				}
				if (canRotate(hitSide)) {
					return hitSide
				}
			case 4 =>
			case 5 =>
				if (hit.z < 0.25) {
					if (hit.y < 0.25) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (hit.y > 0.75) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (canRotate(2)) {
						return 2
					}
				}
				if (hit.z > 0.75) {
					if (hit.y < 0.25) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (hit.y > 0.75) {
						if (canRotate(tBack)) {
							return tBack
						}
					}
					if (canRotate(3)) {
						return 3
					}
				}
				if (hit.y < 0.25) {
					if (canRotate(0)) {
						return 0
					}
				}
				if (hit.y > 0.75) {
					if (canRotate(1)) {
						return 1
					}
				}
				if (canRotate(hitSide)) {
					return hitSide
				}
		}
		return -1
	}

	def canRotate(ord: Int): Boolean = (rotationMask & (1 << ord)) != 0
}
package com.resonant.wrapper.core.content

import java.util.Optional

import com.resonant.core.structure.Structure
import com.resonant.wrapper.core.ResonantEngine
import nova.core.block.Block
import nova.core.block.component.Oriented
import nova.core.entity.Entity
import nova.core.game.Game
import nova.core.network.{Packet, PacketHandler}
import nova.core.render.texture.Texture
import nova.core.util.transform.vector.Vector3d
import nova.core.util.{Category, Direction}

object BlockCreativeBuilder {
	var schematics: Seq[Structure] = Seq.empty
}

class BlockCreativeBuilder extends Block with PacketHandler with Category {

	add(new Oriented(this).setMask(0x3F))

	/**
	 * Called when the block is right clicked by the player
	 */
	override def onRightClick(entity: Entity, side: Int, hit: Vector3d): Boolean = {
		Game.instance.guiFactory.showGui("creativeBuilder", entity, position)
		return true
	}

	override def read(packet: Packet) {
		super.read(packet)
		if (Game.instance.networkManager.isServer && packet.getID == 1) {
			val schematicID = packet.readInt
			val size = packet.readInt
			val buildMap = BlockCreativeBuilder.schematics(schematicID).getBlockStructure
			buildMap.foreach(kv => {
				val placement = position + kv._1
				world.setBlock(placement, kv._2)
			})
		}
	}

	override def getTexture(side: Direction): Optional[Texture] = Optional.of(ResonantEngine.textureCreativeBuilder)

	override def getID: String = "creativeBuilder"

	override def getCategory: String = "tools"
}
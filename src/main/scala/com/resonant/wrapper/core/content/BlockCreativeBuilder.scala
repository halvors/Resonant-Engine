package com.resonant.wrapper.core.content

import java.util.Optional

import com.resonant.core.structure.Structure
import com.resonant.lib.WrapFunctions
import WrapFunctions._
import com.resonant.wrapper.core.{Content, ResonantEngine}
import nova.core.block.Block
import nova.core.block.Block.RightClickEvent
import nova.core.block.component.StaticBlockRenderer
import nova.core.component.Category
import nova.core.component.transform.Orientation
import nova.core.game.Game
import nova.core.network.{Packet, PacketHandler}
import nova.core.util.Direction

object BlockCreativeBuilder {
	var schematics: Seq[Structure] = Seq.empty
}

class BlockCreativeBuilder extends Block with PacketHandler {

	add(new Orientation(this).setMask(0x3F))

	add(new StaticBlockRenderer(this).setTexture(func((dir: Direction) => Optional.of(Content.textureCreativeBuilder))))

	add(new Category("tools"))

	rightClickEvent.add((evt: RightClickEvent) => onRightClick(evt))

	/**
	 * Called when the block is right clicked by the player
	 */
	def onRightClick(evt: RightClickEvent) {
		Game.instance.guiFactory.showGui("creativeBuilder", evt.entity, transform.position)
		evt.result = true
	}

	override def read(packet: Packet) {
		super.read(packet)
		if (Game.instance.networkManager.isServer && packet.getID == 1) {
			val schematicID = packet.readInt
			val size = packet.readInt
			val buildMap = BlockCreativeBuilder.schematics(schematicID).getBlockStructure
			buildMap.foreach(kv => {
				val placement = transform.position + kv._1
				world.setBlock(placement, kv._2)
			})
		}
	}

	override def getID: String = "creativeBuilder"
}
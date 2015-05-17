package com.calclavia.graph.mcwrawpper.redstone

import com.calclavia.graph.api.NodeProvider
import com.calclavia.graph.core.base.NodeBlockConnect
import net.minecraft.world.World
import nova.core.block.Block
import nova.wrapper.mc1710.backward.world.BWWorld

/**
 * A Minecraft implementation that wraps Redstone to a Node
 * @author Calclavia
 */
class NodeRedstone(parent: NodeProvider) extends NodeBlockConnect[com.calclavia.graph.api.energy.NodeRedstone](parent) with com.calclavia.graph.api.energy.NodeRedstone {

	var strongPower = 0
	var weakPower = 0

	override def getStrongPower: Int = mcWorld.getBlockPowerInput(position.xi, position.yi, position.zi)

	override def setStrongPower(power: Int) {
		strongPower = power
		world.markChange(position)
	}

	def mcWorld: World = block.world().asInstanceOf[BWWorld].world()

	def block: Block = parent.asInstanceOf[Block]

	override def getWeakPower(side: Int): Int = mcWorld.getIndirectPowerLevelTo(position.xi, position.yi, position.zi, side)

	override def getWeakPower: Int = mcWorld.getStrongestIndirectPower(position.xi, position.yi, position.zi)

	override def setWeakPower(power: Int) {
		weakPower = power
		world.markChange(position)
	}

	def recache() {

	}

}

package com.calclavia.graph.core.thermal

import com.calclavia.graph.api.NodeProvider
import com.calclavia.graph.core.base.NodeBlockConnect

/**
 * @author Calclavia
 */
class NodeRedstone(parent: NodeProvider) extends NodeBlockConnect[com.calclavia.graph.api.energy.NodeRedstone](parent) with com.calclavia.graph.api.energy.NodeRedstone {

	var redstoneLevel = 0

	override def energy(): Int = redstoneLevel

	override def setOutputEnergy() {

	}
}

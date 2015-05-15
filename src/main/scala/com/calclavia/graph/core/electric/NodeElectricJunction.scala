package com.calclavia.graph.core.electric

import com.calclavia.graph.api.NodeProvider
import com.calclavia.graph.core.electric.component.Junction

/**
 * Wires are getNodes in the grid that will not have different terminals, but instead can connect omni-directionally.
 * Wires will be treated as junctions and collapsed.
 * @author Calclavia
 */
class NodeElectricJunction(parent: NodeProvider) extends NodeAbstractElectric(parent) with com.calclavia.graph.api.energy.NodeElectricJunction {

	var junction: Junction = null

	override def current: Double = voltage * voltage / resistance

	override def voltage: Double = junction.voltage

	override def toString: String = {
		"ElectricJunction [" + connections.size() + ", " + BigDecimal(voltage).setScale(2, BigDecimal.RoundingMode.HALF_UP) + "V]"
	}
}

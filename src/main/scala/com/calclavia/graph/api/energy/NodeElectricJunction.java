package com.calclavia.graph.api.energy;

import nova.core.block.Block;

/**
 * An electric node that acts as a junction (electrical wires) in an electric circuit.
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface NodeElectricJunction extends NodeElectric {

	@Override
	default String getID() {
		return "electricJunction";
	}
}

package com.calclavia.graph.api.energy;

import nova.core.block.Block;

/**
 * An electric node that acts as a component in an electric circuit.
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface NodeElectricComponent extends NodeElectric {

	@Override
	default String getID() {
		return "electricComponent";
	}
}

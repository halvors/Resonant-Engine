package com.calclavia.graph.api.energy;

import com.calclavia.graph.api.Node;
import nova.core.block.Block;

/**
 * A node that can handle Redstone integer-based energy.
 *
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface NodeRedstone extends Node<NodeRedstone> {

	/**
	 * @return The current Redstone energy in this block.
	 */
	int energy();

	/**
	 * @return Sets the block to output Redstone energy.
	 */
	void setOutputEnergy();

	@Override
	default String getID() {
		return "redstone";
	}
}

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

	int getStrongPower();

	/**
	 * Sets the block to output strong Redstone power.
	 */
	void setStrongPower(int power);

	/**
	 * @return The current Redstone energy powered to a specific side of this block.
	 */
	int getWeakPower(int side);

	/**
	 * @return The greatest Redstone energy indirectly powering this block.
	 */
	int getWeakPower();

	/**
	 * Sets the block to output weak Redstone power.
	 */
	void setWeakPower(int power);

	@Override
	default String getID() {
		return "redstone";
	}
}

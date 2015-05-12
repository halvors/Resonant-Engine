package com.calclavia.graph.api.energy;

import com.calclavia.graph.api.Node;
import nova.core.block.Block;

/**
 * An electric node that acts as an electric circuit.
 * Constructor requirement: Provider (An instance of {@link Block}
 * @author Calclavia
 */
public interface NodeElectric extends Node<NodeElectric> {

	/**
	 * @return The resistance of the electric component in ohms.
	 */
	double resistance();

	/**
	 * @return The voltage (potential difference) of the component in volts.
	 */
	double voltage();

	/**
	 * @return The current of the component in amperes.
	 */
	double current();

	/**
	 * @return The power dissipated in the component.
	 */
	default double power() {
		return current() * voltage();
	}
}

package com.calclavia.graph.api.energy;

import com.calclavia.graph.api.Node;

/**
 * An abstract interface extended by NodeElectricComponent and NodeElectricJunction.
 * This interface is NOT registered.
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

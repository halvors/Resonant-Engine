package com.calclavia.graph.api;

import nova.core.util.Direction;

import java.util.Set;

/**
 * A node provider is implemented in blocks or entities.
 * A node provider provides a node that is associated with another object.
 * @author Calclavia
 */
public interface NodeProvider {
	/**
	 * @param nodeType - The type of node we are looking for.
	 * @param from - The direction.
	 * @return Returns the node object.
	 */
	@Deprecated
	default <N extends Node> N getNode(Class<? extends N> nodeType, Direction from) {
		return getNodes(from).stream()
			.filter(n -> nodeType.getClass().isAssignableFrom(n.getClass()))
			.map(n -> (N) n)
			.findFirst()
			.orElse(null);
	}

	/**
	 * Gets a list of getNodes that this NodeProvider provides.
	 * @param from - The direction being accessed
	 * @return - A set of getNodes.
	 */
	Set<Node> getNodes(Direction from);
}

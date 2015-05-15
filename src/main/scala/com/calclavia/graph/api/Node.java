package com.calclavia.graph.api;

import nova.core.util.Identifiable;

import java.util.Set;

/**
 * A node is a object with connections in a graph structure.
 */
public interface Node<CONNECTION extends Node<?>> extends Identifiable {

	/**
	 * Gets a list of getNodes connected to this node.
	 */
	Set<CONNECTION> connections();
}

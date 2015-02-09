package com.resonant.core.graph.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * A graph that contains nodes, each with its ability to connect to other nodes.
 *
 * @author Calclavia
 */
public abstract class GraphConnect<N extends Node> implements Graph<N> {

	//A map of nodes and their connections
	protected Map<N, Set<N>> nodeMap = new HashMap<>();
	//A queue of nodes to be added to the grid upon next build()
	private Set<N> addQueue = Collections.newSetFromMap(new WeakHashMap<>());

	@Override
	public void add(N node) {
		nodeMap.remove(node);
	}

	@Override
	public void remove(N node) {
		nodeMap.remove(node);
	}

	@Override
	public Set<N> nodes() {
		return nodeMap.keySet();
	}

	@Override
	public void build() {
		addQueue.forEach(this::populate);
		addQueue.clear();
	}

	/**
	 * Populates the node list recursively
	 */
	protected void populate(N node) {
		populate(node, null);
	}

	protected void populate(N node, N prev) {
		if (!nodes().contains(node)) {
			Set<N> connections = node.connections();
			nodeMap.put(node, connections);
			connections.forEach(n -> populate(n, node));
		}
	}
}

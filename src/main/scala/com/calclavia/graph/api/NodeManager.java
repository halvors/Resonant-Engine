package com.calclavia.graph.api;

import nova.core.util.Factory;
import nova.core.util.Manager;
import nova.core.util.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A reference to NodeManager can be obtained through dependency injection.
 *
 * Registered node types:
 * "redstone" -> The Minecraft-like discrete Redstone system.
 * "electricComponent" -> An electric circuit component.
 * "electricJunction" -> An electric circuit junction.
 * "thermal" -> A grid that manages heat values.
 * @author Calclavia
 */
public class NodeManager extends Manager<Node<?>, NodeManager.NodeFactory> {

	private Map<Class<Node<?>>, String> classToNode = new HashMap<>();

	private NodeManager(Registry<NodeFactory> registry) {
		super(registry);
	}

	@Override
	public NodeFactory register(Function<Object[], Node<?>> constructor) {
		return register(new NodeFactory(constructor));
	}

	@Override
	public NodeFactory register(NodeFactory factory) {
		classToNode.put((Class<Node<?>>) factory.getDummy().getClass(), factory.getID());
		return super.register(factory);
	}

	/**
	 * Instantiates a new node based on its interface. This is not as reliable as make with nodeID.
	 * @param nodeInterface - The interface assosiated with the new
	 * @param args - The arguments for the constructor
	 * @param <N> - The node type
	 * @return A new node of N type.
	 */
	public <N extends Node> N make(Class<N> nodeInterface, Object... args) {
		return (N) registry.stream()
						   .filter(n -> nodeInterface.isAssignableFrom(n.getDummy().getClass()))
						   .findFirst()
						   .get()
						   .make(args);
	}

	/**
	 * Instantiates a new node based on its nodeID.
	 * @param nodeID - The ID of the node
	 * @param args - The arguments for the constructor
	 * @return A new node.
	 */
	public Node<?> make(String nodeID, Object... args) {
		return registry.get(nodeID).get().make(args);
	}

	public class NodeFactory extends Factory<Node<?>> {
		public NodeFactory(Function<Object[], Node<?>> constructor) {
			super(constructor);
		}

		public Node<?> make(Object... args) {
			return constructor.apply(args);
		}
	}
}

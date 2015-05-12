package com.calclavia.graph.core;

import com.calclavia.graph.api.NodeManager;
import com.calclavia.graph.api.NodeProvider;
import com.calclavia.graph.core.electric.NodeElectricComponent;
import nova.core.loader.Loadable;
import nova.core.loader.NovaMod;

/**
 * The main plugin loader class.
 * @author Calclavia
 */
@NovaMod(id = "nodeAPI", name = "Node API", version = "0.0.1", novaVersion = "0.0.1", modules = { NodeModule.class }, isPlugin = true)
public class NodeAPI implements Loadable {

	private final NodeManager nodeManager;

	public NodeAPI(NodeManager nodeManager) {
		this.nodeManager = nodeManager;
	}

	@Override
	public void preInit() {
		nodeManager.register(args -> {
			if (args.length > 0) {
				return new NodeElectricComponent((NodeProvider) args[0]);
			} else {
				return new NodeElectricComponent(null);
			}
		});
	}
}

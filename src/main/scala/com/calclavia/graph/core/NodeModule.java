package com.calclavia.graph.core;

import com.calclavia.graph.api.NodeManager;
import se.jbee.inject.bind.BinderModule;
import se.jbee.inject.util.Scoped;

/**
 * The NOVA dependency injection module.
 * @author Calclavia
 */
public class NodeModule extends BinderModule {

	@Override
	protected void declare() {
		per(Scoped.APPLICATION).bind(NodeManager.class).toConstructor();
	}

}

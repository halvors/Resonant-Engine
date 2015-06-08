package com.resonant.core.prefab.block.multiblock;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import nova.core.world.World;

public interface IMultiBlockStructure<W extends IMultiBlockStructure> extends IMultiBlock {
	public World world();

	public void onMultiBlockChanged();

	public Vector3D position();

	public MultiBlockHandler<W> getMultiBlock();
}

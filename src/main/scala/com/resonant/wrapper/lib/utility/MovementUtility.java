package com.resonant.wrapper.lib.utility;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.lang.reflect.Method;

/**
 * Helper class for the Force Manipulator
 * @author Calclavia
 * @author Based off of Jakj's code. Licensed under WTFPL.
 */
//TODO: Move wrapper
public class MovementUtility {
	/**
	 * Obfuscation names for reflection
	 */
	public static final String[] CHUNK_RELIGHT_BLOCK = { "relightBlock", "func_76615_h" };
	public static final String[] CHUNK_PROPOGATE_SKY_LIGHT_OCCLUSION = { "propagateSkylightOcclusion", "func_76595_e" };

	//TODO: CB Microblock support
	/**
	 *

	 val isMultipart: Boolean = this.tileData.getString("id") == "savedMultipart"
	 var newTile: TileEntity = null
	 if (isMultipart)
	 {
	 try
	 {
	 val multipart: Class[_] = Class.forName("codechicken.multipart.MultipartHelper")
	 val m: Method = multipart.getMethod("createTileFromNBT", classOf[World], classOf[NBTTagCompound])
	 newTile = m.invoke(null, startPosition.world, this.tileData).asInstanceOf[TileEntity]
	 }
	 catch
	 {
	 case e: Exception =>
	 {
	 e.printStackTrace
	 }
	 }
	 }
	 else
	 {
	 newTile = TileEntity.createAndLoadEntity(this.tileData)
	 }*/
	/*
	if (newTile != null && isMultipart)
	{
		try
		{
			val multipart: Class[_] = Class.forName("codechicken.multipart.MultipartHelper")
			multipart.getMethod("sendDescPacket", classOf[World], classOf[TileEntity]).invoke(null, startPosition.world, newTile)
			val tileMultipart: Class[_] = Class.forName("codechicken.multipart.TileMultipart")
			tileMultipart.getMethod("onMoved").invoke(newTile)
		}
		catch
		{
			case e: Exception =>
			{
				e.printStackTrace
			}
		}
	}*/

	/**
	 * Sets a block in a sneaky way to bypass some restraints.
	 */
	public static void setBlockSneaky(World world, Vector3D position, Block block, int metadata, TileEntity tileEntity) {
		if (block != null && world != null) {
			Chunk chunk = world.getChunkFromChunkCoords((int) position.getX() >> 4, (int) position.getZ() >> 4);
			Vector3D chunkPosition = new Vector3D((int) position.getX() & 0xF, (int) position.getY() & 0xF, (int) position.getZ() & 0xF);

			int heightMapIndex = (int) chunkPosition.getZ() << 4 | (int) chunkPosition.getX();

			if ((int) position.getY() >= chunk.precipitationHeightMap[heightMapIndex] - 1) {
				chunk.precipitationHeightMap[heightMapIndex] = -999;
			}

			int heightMapValue = chunk.heightMap[heightMapIndex];

			world.removeTileEntity((int) position.getX(), (int) position.getY(), (int) position.getZ());

			ExtendedBlockStorage extendedBlockStorage = chunk.getBlockStorageArray()[(int) position.getY() >> 4];

			if (extendedBlockStorage == null) {
				extendedBlockStorage = new ExtendedBlockStorage(((int) position.getY() >> 4) << 4, !world.provider.hasNoSky);

				chunk.getBlockStorageArray()[(int) position.getY() >> 4] = extendedBlockStorage;
			}

			extendedBlockStorage.func_150818_a((int) chunkPosition.getX(), (int) chunkPosition.getY(), (int) chunkPosition.getZ(), block);
			extendedBlockStorage.setExtBlockMetadata((int) chunkPosition.getX(), (int) chunkPosition.getY(), (int) chunkPosition.getZ(), metadata);

			if ((int) position.getY() >= heightMapValue) {
				chunk.generateSkylightMap();
			} else {
				//chunk.getBlockLightOpacity(chunkPosition.getX(), (int)position.getY(), chunkPosition.zi())
				if (chunk.getBlockLightValue((int) chunkPosition.getX(), (int) position.getY(), (int) chunkPosition.getZ(), 0) > 0) {
					if ((int) position.getY() >= heightMapValue) {
						relightBlock(chunk, chunkPosition.add(new Vector3D(0, 1, 0)));
					}
				} else if ((int) position.getY() == heightMapValue - 1) {
					relightBlock(chunk, chunkPosition);
				}

				propagateSkylightOcclusion(chunk, chunkPosition);
			}

			chunk.isModified = true;
			//updateAllLightTypes
			world.func_147451_t((int) position.getX(), (int) position.getY(), (int) position.getZ());

			if (tileEntity != null) {
				world.setTileEntity((int) position.getX(), (int) position.getY(), (int) position.getZ(), tileEntity);
			}

			world.markBlockForUpdate((int) position.getX(), (int) position.getY(), (int) position.getZ());
		}
	}

	/**
	 * Re-lights the block in a specific position.
	 * @param chunk
	 * @param position
	 */
	public static void relightBlock(Chunk chunk, Vector3D position) {
		try {
			Method m = ReflectionHelper.findMethod(Chunk.class, null, CHUNK_RELIGHT_BLOCK, int.class, int.class, int.class);
			m.invoke(chunk, (int) position.getX(), (int) position.getY(), (int) position.getZ());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Propogates skylight occlusion in a specific chunk's position.
	 * @param chunk
	 * @param position
	 */
	public static void propagateSkylightOcclusion(Chunk chunk, Vector3D position) {
		try {
			Method m = ReflectionHelper.findMethod(Chunk.class, null, CHUNK_PROPOGATE_SKY_LIGHT_OCCLUSION, int.class, int.class);
			m.invoke(chunk, (int) position.getX(), (int) position.getZ());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

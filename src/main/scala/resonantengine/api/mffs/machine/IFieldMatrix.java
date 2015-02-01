package resonantengine.api.mffs.machine;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import nova.core.util.transform.Vector3d;
import resonantengine.api.mffs.modules.IModule;
import resonantengine.api.mffs.modules.IModuleProvider;
import resonantengine.api.mffs.modules.IProjectorMode;

import java.util.Set;

public interface IFieldMatrix extends IModuleProvider, IActivatable, IPermissionProvider
{
	/**
	 * Gets the mode of the projector, mainly the shape and size of it.
	 */
	public IProjectorMode getMode();

	public ItemStack getModeStack();

	/**
	 * Gets the slot IDs based on the direction given.
	 */
	public int[] getDirectionSlots(ForgeDirection direction);

	/**
	 * Gets the unspecified, direction-unspecific module slots on the left side of the GUI.
	 */
	public int[] getModuleSlots();

	/**
	 * @param module    - The module instance.
	 * @param direction - The direction facing.
	 * @return Gets the amount of modules based on the side.
	 */
	public int getSidedModuleCount(IModule module, ForgeDirection... direction);

	/**
	 * Transformation information functions. Returns CACHED information unless the cache is cleared.
	 * Note that these are all RELATIVE to the projector's position.
	 */
	public Vector3d getTranslation();

	public Vector3d getPositiveScale();

	public Vector3d getNegativeScale();

	public int getRotationYaw();

	public int getRotationPitch();

	/**
	 * @return Gets all the absolute block coordinates that are occupying the force field. Note that this is a copy of the actual field set.
	 */
	public Set<Vector3d> getCalculatedField();

	/**
	 * Gets the absolute interior points of the projector. This might cause lag so call sparingly.
	 *
	 * @return
	 */
	public Set<Vector3d> getInteriorPoints();

	/**
	 * @return Gets the facing direction. Always returns the front side of the block.
	 */
	public ForgeDirection getDirection();

}

package resonantengine.lib.render.block

import nova.core.util.transform.Vector3d
import resonantengine.lib.modcontent.block.ResonantBlock
import resonantengine.lib.render.{RenderBlockUtility, RenderUtility}
import resonantengine.lib.wrapper.BitmaskWrapper._

/**
 * A generic TileEntity connected texture renderer.
 * Created by Calclavia on 3/22/2014.
 */
trait RenderConnectedTexture extends ResonantBlock
{
  var faceTexture: String = null
  var edgeTexture: String = null

  override def renderInventory(itemStack: ItemStack)
  {
    glPushMatrix()
    RenderBlockUtility.tessellateBlockWithConnectedTextures(itemStack.getItemDamage, tile.block, if(faceTexture != null) RenderUtility.getIcon(faceTexture) else null, RenderUtility.getIcon(edgeTexture))
    glPopMatrix()
  }

  /**
   * Render the static, unmoving faces of this part into the world renderer.
   * The Tessellator is already drawing.
   * @return true if vertices were added to the tessellator
   */
  override def renderStatic(renderer: RenderBlocks, pos: Vector3d, pass: Int): Boolean =
  {
    var sideMap = 0

    for (dir <- ForgeDirection.VALID_DIRECTIONS)
    {
      val check = position + dir
      val checkTile = check.getTileEntity

      if (checkTile != null && checkTile.getClass == tile.getClass && check.getBlockMetadata(world) == tile.getBlockMetadata)
      {
        sideMap = sideMap.openMask(dir)
      }
    }

    RenderBlockUtility.tessellateBlockWithConnectedTextures(sideMap, world, pos.xi, pos.yi, pos.zi, tile.getBlockType, if(faceTexture != null) RenderUtility.getIcon(faceTexture) else null, RenderUtility.getIcon(edgeTexture))
    return true
  }
}

package resonant.lib.grid.branch

import net.minecraftforge.common.util.ForgeDirection
import resonant.api.grid.{IGrid, INodeGrid, INodeProvider}
import resonant.engine.References
import resonant.lib.grid.branch.part.Branch
import resonant.lib.grid.node.Node
import resonant.lib.prefab.TConnector
;

/**
 * Created by robert on 11/12/2014.
 */
class NodeBranchPart(parent: INodeProvider) extends Node(parent) with TConnector with INodeGrid
{
  var branch : Branch = null
  var grid : BranchedGrid = null

  /** Gets the branch that contains this node */
  def getBranch : Branch = branch

  /** Sets the branch that will contain this node */
  def setBranch(b : Branch)
  {
    branch = b
  }

  /** Sets the grid reference */
  override def setGrid(grid: IGrid[_]):Unit =
  {
    if(grid.isInstanceOf[BranchedGrid])
      this.grid = grid.asInstanceOf[BranchedGrid]
  }

  /** Gets the grid reference */
  override def getGrid: BranchedGrid =
  {
    if(grid == null)
    {
      grid = new BranchedGrid()
      grid.add(this)
    }
    return grid
  }

  /** Is this connector allowed to connect to any side
    * @param connector - any connecting object, Most likely TileEntity, Node, INodeProvider */
  override protected def isValidConnector(connector: Object): Boolean =
  {
    return connector != null && connector.isInstanceOf[NodeBranchPart];
  }

  override def reconstruct()
  {
    References.LOGGER.debug("NodeBranchPart: Reconstruct")
    super.reconstruct()
    buildConnections()
  }

  override def connect(obj: Object, dir: ForgeDirection)
  {
    References.LOGGER.debug("NodeBranchPart: connect " + obj +"   dir")
    super.connect(obj, dir)
    if(obj.isInstanceOf[INodeProvider])
    {
      val node : NodeBranchPart = obj.asInstanceOf[INodeProvider].getNode(classOf[NodeBranchPart], dir)
      if(node != null)
      {
        node.getGrid.merge(getGrid)
      }
    }
    else if(obj.isInstanceOf[NodeBranchPart])
    {
      obj.asInstanceOf[NodeBranchPart].getGrid.merge(getGrid)
    }
  }
}
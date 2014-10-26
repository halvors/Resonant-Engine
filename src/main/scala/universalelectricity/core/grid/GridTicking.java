package universalelectricity.core.grid;

import universalelectricity.api.core.grid.IUpdate;
import universalelectricity.core.grid.node.Node;

/**
 * Created by robert on 8/11/2014.
 */
public class GridTicking<N extends Node> extends GridNode<N> implements IUpdate {

    public GridTicking(Class node)
    {
        super(node);
        UpdateTicker.addUpdater(this);
    }

    @Override
    public void update(double deltaTime)
    {
        for(N node : this.getNodes())
        {
            if(node instanceof IUpdate && ((IUpdate) node).canUpdate())
                ((IUpdate)node).update(deltaTime);
        }
    }

    @Override
    public boolean canUpdate() {return getNodes().size() > 0; }

    @Override
    public boolean continueUpdate() { return canUpdate();}
}

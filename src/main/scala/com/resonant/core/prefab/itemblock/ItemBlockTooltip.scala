package com.resonant.core.prefab.itemblock

import com.resonant.core.prefab.item.TItemToolTip
import nova.core.block.Block
import nova.core.item.ItemBlock

class ItemBlockTooltip(block: Block) extends ItemBlock(block) with TItemToolTip {
}
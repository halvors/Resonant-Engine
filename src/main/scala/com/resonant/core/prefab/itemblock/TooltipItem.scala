package com.resonant.core.prefab.itemblock

import com.resonant.lib.WrapFunctions
import com.resonant.lib.WrapFunctions._
import com.resonant.wrapper.lib.wrapper.StringWrapper._
import nova.core.gui.InputManager.Key
import nova.core.item.Item
import nova.core.item.Item.TooltipEvent
import nova.core.render.Color
import nova.internal.Game

/**
 * @author Calclavia
 */
trait TooltipItem extends Item {

	tooltipEvent.add(eventListener((evt: TooltipEvent) => {
		val tooltipID = getID + ".tooltip"
		val tooltip = tooltipID.getLocal

		if (tooltip != null && !tooltip.isEmpty && !tooltip.equals(tooltipID)) {
			//TODO: Bad reference to game?
			if (!Game.input.isKeyDown(Key.KEY_LSHIFT)) {
				evt.tooltips.add("tooltip.noShift".getLocal.replace("#0", Color.blue.toString).replace("#1", Color.gray.toString))
			}
			else {
				evt.tooltips.addAll(tooltip.listWrap(20))
			}
		}
	}))
}

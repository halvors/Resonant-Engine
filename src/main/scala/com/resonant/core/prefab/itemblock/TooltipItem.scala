package com.resonant.core.prefab.itemblock

import nova.scala.wrapper.FunctionalWrapper
import FunctionalWrapper._
import nova.scala.wrapper.StringWrapper._
import nova.core.gui.InputManager.Key
import nova.core.item.Item
import nova.core.item.Item.TooltipEvent
import nova.core.render.Color
import nova.internal.Game
import nova.scala.wrapper.FunctionalWrapper

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

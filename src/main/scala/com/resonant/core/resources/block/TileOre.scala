package com.resonant.core.resources.block

import java.util.Optional

import com.resonant.core.resources.{Resource, ResourceFactory}
import com.resonant.lib.wrapper.WrapFunctions._
import nova.core.block.Block
import nova.core.block.component.StaticBlockRenderer
import nova.core.render.Color
import nova.core.render.model.{BlockModelUtil, Model}
import nova.core.util.Direction

/**
 * A generic ore block that is automatically colored/textured based on a color multiplier.
 * @author Calclavia
 */
class TileOre extends Block with Resource {
	var renderingForeground = false

	add(
		new StaticBlockRenderer(this) {
			override def renderStatic(model: Model) {
				renderingForeground = false
				BlockModelUtil.drawBlock(model, TileOre.this)
				renderingForeground = true
				BlockModelUtil.drawBlock(model, TileOre.this)
			}
		}
			.setColorMultiplier((side: Direction) => if (renderingForeground) Color.argb(ResourceFactory.getColor(material)) else Color.white)
			.setTexture(func((side: Direction) => if (renderingForeground) Optional.of(ResourceFactory.oreForeground) else Optional.of(ResourceFactory.oreBackground)))
	)
}

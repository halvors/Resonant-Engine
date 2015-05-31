package com.resonant.wrapper.core.content

import java.util.Optional
import _root_.tools.IToolWrench
import com.resonant.wrapper.core.{Content, ResonantEngine}
import net.minecraft.entity.player.EntityPlayer
import nova.core.component.Category
import nova.core.item.Item
import nova.core.render.texture.ItemTexture

class ItemScrewdriver extends Item with IToolWrench {
	add(new Category("tools"))

	override def getID: String = "screwdriver"

	override def canWrench(entityPlayer: EntityPlayer, x: Int, y: Int, z: Int): Boolean = true

	override def wrenchUsed(entityPlayer: EntityPlayer, x: Int, y: Int, z: Int) {
	}

	override def getTexture: Optional[ItemTexture] = Optional.of(Content.textureScrewdriver)
}
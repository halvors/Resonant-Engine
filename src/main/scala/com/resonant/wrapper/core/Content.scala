package com.resonant.wrapper.core

import com.resonant.core.prefab.modcontent.ContentLoader
import com.resonant.wrapper.core.content.{BlockCreativeBuilder, ItemScrewdriver}
import nova.core.block.BlockFactory
import nova.core.item.ItemFactory
import nova.core.render.texture.{BlockTexture, ItemTexture}

/**
 * @author Calclavia
 */
object Content extends ContentLoader {

	val textureCreativeBuilder = new BlockTexture(Reference.id, "creativeBuilder")
	val textureScrewdriver = new ItemTexture(Reference.id, "screwdriver")
	val blockCreativeBuilder: BlockFactory = classOf[BlockCreativeBuilder]
	val itemScrewdriver: ItemFactory = classOf[ItemScrewdriver]

	override def id: String = Reference.id
}

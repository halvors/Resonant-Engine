package com.resonant.core.prefab.modcontent

import java.util.function.{Function => JFunction}

import com.resonant.lib.WrapFunctions
import WrapFunctions._
import nova.core.block.{Block, BlockFactory}
import nova.core.entity.{Entity, EntityFactory}
import nova.core.game.Game
import nova.core.gui.Gui
import nova.core.gui.factory.GuiFactory
import nova.core.item.{Item, ItemFactory}
import nova.core.loader.Loadable
import nova.core.render.model.ModelProvider
import nova.core.render.texture.{BlockTexture, ItemTexture}

/**
 * Automatic mffs.content registration for all Blocks, Items, Entities and Textures.
 *
 * Extend this trait from the main mod loading class and all fields will be registered. Elegantly.
 *
 * @author Calclavia
 */
trait ContentLoader extends Loadable {
	self =>

	def id: String

	override def preInit() = {
		//Automated handler for registering blocks & items vars
		for (field <- self.getClass.getDeclaredFields) {
			//Set it so we can access the field
			field.setAccessible(true)

			//Get contents for reference
			val obj = field.get(self)

			if (obj != null) {
				// Get type of AnyRef, then register it if supported
				obj match {
					case itemWrapper: ItemClassWrapper =>
						if (itemWrapper.wrapped.newInstance().isInstanceOf[AutoItemTexture]) {
							val texture = Game.renderManager.registerTexture(new ItemTexture(id, itemWrapper.getID))
							field.set(self, Game.itemManager.register(
								(args: Array[AnyRef]) => {
									val wrapped = itemWrapper.wrapped.newInstance()
									wrapped.asInstanceOf[AutoItemTexture].texture = texture
									wrapped
								}
							))
						}
						else {
							field.set(self, Game.itemManager.register(itemWrapper.wrapped))
						}
					case itemConstructor: ItemConstructorWrapper =>
						if (itemConstructor.wrapped.apply().isInstanceOf[AutoItemTexture]) {
							val texture = Game.renderManager.registerTexture(new ItemTexture(id, itemConstructor.wrapped.getID))
							field.set(self, Game.itemManager.register(
								(args: Array[AnyRef]) => {
									val wrapped = itemConstructor.wrapped.apply()
									wrapped.asInstanceOf[AutoItemTexture].texture = texture
									wrapped
								}
							))
						}
						else {
							field.set(self, Game.itemManager.register(itemConstructor))
						}

					case blockWrapper: BlockClassWrapper =>
						if (blockWrapper.wrapped.newInstance().isInstanceOf[AutoBlockTexture]) {
							val texture = Game.renderManager.registerTexture(new BlockTexture(id, blockWrapper.getID))
							Game.renderManager.registerTexture(new BlockTexture(id, blockWrapper.getID))
							field.set(self, Game.blockManager.register(
								(args: Array[AnyRef]) => {
									val wrapped = blockWrapper.wrapped.newInstance()
									wrapped.asInstanceOf[AutoBlockTexture].texture = texture
									wrapped
								}
							))
						}
						else {
							field.set(self, Game.blockManager.register(blockWrapper.wrapped))
						}
					case blockConstructor: BlockConstructorWrapper =>
						if (blockConstructor.wrapped.apply().isInstanceOf[AutoBlockTexture]) {
							val texture = Game.renderManager.registerTexture(new BlockTexture(id, blockConstructor.getID))
							Game.renderManager.registerTexture(new BlockTexture(id, blockConstructor.getID))
							field.set(self, Game.blockManager.register(
								(args: Array[AnyRef]) => {
									val wrapped = blockConstructor.wrapped.apply()
									wrapped.asInstanceOf[AutoBlockTexture].texture = texture
									wrapped
								}
							))
						}
						else {
							field.set(self, Game.blockManager.register(blockConstructor))
						}
					case factory: EntityClassWrapper => field.set(self, Game.entityManager.register(factory))
					case factory: EntityConstructorWrapper => field.set(self, Game.entityManager.register(factory))
					case itemTexture: ItemTexture => field.set(self, Game.renderManager.registerTexture(itemTexture))
					case blockTexture: BlockTexture => field.set(self, Game.renderManager.registerTexture(blockTexture))
					case modelProvider: ModelProvider => field.set(self, Game.renderManager.registerModel(modelProvider))
					case guiFactory: GuiConstructorWrapper => Game.guiFactory.register(guiFactory)
					case _ =>
				}
			}
		}
	}

	/**
	 * Creates a dummy instances temporarily until the preInit stage has passed
	 */
	implicit protected class BlockClassWrapper(val wrapped: Class[_ <: Block]) extends BlockFactory((args: Array[AnyRef]) => wrapped.newInstance())

	implicit protected class BlockConstructorWrapper(val wrapped: () => Block) extends BlockFactory((args: Array[AnyRef]) => wrapped())

	implicit protected class ItemClassWrapper(val wrapped: Class[_ <: Item]) extends ItemFactory((args: Array[AnyRef]) => wrapped.newInstance())

	implicit protected class ItemConstructorWrapper(val wrapped: () => Item) extends ItemFactory((args: Array[AnyRef]) => wrapped())

	implicit protected class EntityClassWrapper(val wrapped: Class[_ <: Entity]) extends EntityFactory((args: Array[AnyRef]) => wrapped.newInstance())

	implicit protected class EntityConstructorWrapper(val wrapped: () => Entity) extends EntityFactory((args: Array[AnyRef]) => wrapped())

	implicit protected class GuiConstructorWrapper(val wrapped: Class[_ <: Gui]) extends GuiFactory((args: Array[AnyRef]) => wrapped.newInstance())
}

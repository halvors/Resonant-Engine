package com.resonant.core.prefab.modcontent

import java.util.function.Supplier

import nova.core.block.Block
import nova.core.entity.{Entity, EntityFactory}
import nova.core.game.Game
import nova.core.item.Item
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
				// Get type of object, then register it if supported
				obj match {
					case itemWrapper: ItemClassWrapper =>
						if (itemWrapper.wrapped.newInstance().isInstanceOf[AutoItemTexture]) {
							val texture = Game.instance.renderManager.registerTexture(new ItemTexture(id, itemWrapper.getID))
							field.set(self, Game.instance.itemManager.register(new Supplier[Item] {
								override def get(): Item = {
									val wrapped = itemWrapper.wrapped.newInstance()
									wrapped.asInstanceOf[AutoItemTexture].texture = texture
									return wrapped
								}
							}))
						}
						else {
							field.set(self, Game.instance.itemManager.register(itemWrapper.wrapped))
						}
					case itemConstructor: ItemConstructorWrapper =>
						if (itemConstructor.wrapped.apply().isInstanceOf[AutoItemTexture]) {
							val texture = Game.instance.renderManager.registerTexture(new ItemTexture(id, itemConstructor.wrapped.getID))
							field.set(self, Game.instance.itemManager.register(new Supplier[Item] {
								override def get(): Item = {
									val wrapped = itemConstructor.wrapped.apply()
									wrapped.asInstanceOf[AutoItemTexture].texture = texture
									return wrapped
								}
							}))
						}
						else {
							field.set(self, Game.instance.itemManager.register(new Supplier[Item] {
								override def get(): Item = itemConstructor.wrapped()
							}))
						}

					case blockWrapper: BlockClassWrapper =>
						if (blockWrapper.wrapped.newInstance().isInstanceOf[AutoBlockTexture]) {
							val texture = Game.instance.renderManager.registerTexture(new BlockTexture(id, blockWrapper.getID))
							Game.instance.renderManager.registerTexture(new BlockTexture(id, blockWrapper.getID))
							field.set(self, Game.instance.blockManager.register(new Supplier[Block] {
								override def get(): Block = {
									val wrapped = blockWrapper.wrapped.newInstance()
									wrapped.asInstanceOf[AutoBlockTexture].texture = texture
									return wrapped
								}
							}))
						}
						else {
							field.set(self, Game.instance.blockManager.register(blockWrapper.wrapped))
						}
					case blockConstructor: BlockConstructorWrapper =>
						if (blockConstructor.wrapped.apply().isInstanceOf[AutoBlockTexture]) {
							val texture = Game.instance.renderManager.registerTexture(new BlockTexture(id, blockConstructor.getID))
							Game.instance.renderManager.registerTexture(new BlockTexture(id, blockConstructor.getID))
							field.set(self, Game.instance.blockManager.register(new Supplier[Block] {
								override def get(): Block = {
									val wrapped = blockConstructor.wrapped.apply()
									wrapped.asInstanceOf[AutoBlockTexture].texture = texture
									return wrapped
								}
							}))
						}
						else {
							field.set(self, Game.instance.blockManager.register(new Supplier[Block] {
								override def get(): Block = blockConstructor.wrapped
							}))
						}
					case factory: EntityClassWrapper => field.set(self, Game.instance.entityManager.register(factory))
					case factory: EntityConstructorWrapper => field.set(self, Game.instance.entityManager.register(factory))
					case itemTexture: ItemTexture => field.set(self, Game.instance.renderManager.registerTexture(itemTexture))
					case blockTexture: BlockTexture => field.set(self, Game.instance.renderManager.registerTexture(blockTexture))
					case modelProvider: ModelProvider => field.set(self, Game.instance.renderManager.registerModel(modelProvider))
					case _ =>
				}
			}
		}
	}

	/**
	 * Creates a dummy instances temporarily until the preInit stage has passed
	 */
	implicit protected class BlockClassWrapper(val wrapped: Class[_ <: Block]) extends Block {
		override def getID: String = wrapped.newInstance().getID
	}

	implicit protected class BlockConstructorWrapper(val wrapped: () => Block) extends Block {
		override def getID: String = wrapped.apply().getID
	}

	implicit protected class ItemClassWrapper(val wrapped: Class[_ <: Item]) extends Item {
		override def getID: String = wrapped.newInstance().getID
	}

	implicit protected class ItemConstructorWrapper(val wrapped: () => Item) extends Item {
		override def getID: String = wrapped.apply().getID
	}

	implicit protected class EntityClassWrapper(val wrapped: Class[_ <: Entity]) extends EntityFactory(new Supplier[Entity] {
		override def get(): Entity = wrapped.newInstance()
	})

	implicit protected class EntityConstructorWrapper(val wrapped: () => Entity) extends EntityFactory(new Supplier[Entity] {
		override def get(): Entity = wrapped()
	})

}

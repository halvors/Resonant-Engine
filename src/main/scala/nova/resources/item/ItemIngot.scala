package nova.resources.item

import java.util.Optional

import com.sun.prism.ResourceFactory
import nova.resources.{Resource, ResourceFactory}
import nova.core.item.Item
import nova.core.render.texture.ItemTexture

/**
 * @author Calclavia
 */
class ItemIngot extends Item with Resource {
	//	override def getTexture: Optional[ItemTexture] = Optional.of(ResourceFactory.ingot)
}

package com.resonant.core.component.renderer

import nova.core.block.Block
import nova.core.component.renderer.StaticRenderer
import nova.core.render.model.{BlockModelUtil, Model}
import nova.core.util.transform.matrix.MatrixStack

/**
 * Renders a block with rotation based on its direction
 * @author Calclavia
 */
class RotatedRenderer(block: Block) extends StaticRenderer(block) {

	override def renderStatic(model: Model) {
		BlockModelUtil.drawBlock(model, block)
		val stack = new MatrixStack()
		stack.loadMatrix(model.matrix)
		stack.rotate(block.getComponent().rotation)
		model.matrix = stack.getMatrix
	}
}

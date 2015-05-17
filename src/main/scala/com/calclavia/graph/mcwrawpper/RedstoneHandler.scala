package com.calclavia.graph.mcwrawpper

import com.calclavia.graph.api.NodeProvider
import com.calclavia.graph.mcwrawpper.redstone.NodeRedstone
import com.resonant.lib.wrapper.WrapFunctions._
import nova.core.event.EventManager.BlockNeighborChangeEvent
import nova.core.game.Game
import nova.core.util.Direction
import nova.core.util.transform.Vector3i
import nova.core.world.World
import nova.wrapper.mc1710.util.WrapperEventManager
import nova.wrapper.mc1710.util.WrapperEventManager.{RedstoneConnectEvent, RedstoneEvent}

import scala.collection.convert.wrapAll._

/**
 * @author Calclavia
 */
class RedstoneHandler {

	def init() {
		Game.instance.eventManager.blockNeighborChange.add(
			(evt: BlockNeighborChangeEvent) => {
				getRedstoneNodes(evt.world, evt.position).foreach(_.recache())
			}
		)

		WrapperEventManager.instance.onCanConnect.add(
			(evt: RedstoneConnectEvent) => {
				//TODO: Null may cause an issue
				evt.canConnect = getRedstoneNodes(evt.world, evt.position).exists(_.canConnect(null, evt.direction))
			}
		)

		WrapperEventManager.instance.onStrongPower.add(
			(evt: RedstoneEvent) => {
				evt.power = getRedstoneNodes(evt.world, evt.position).map(_.strongPower).max
			}
		)

		WrapperEventManager.instance.onWeakPower.add(
			(evt: RedstoneEvent) => {
				evt.power = getRedstoneNodes(evt.world, evt.position).map(_.weakPower).max
			}
		)
	}

	def getRedstoneNodes(world: World, pos: Vector3i): Set[NodeRedstone] = {
		val blockOptional = world.getBlock(pos)
		if (blockOptional.isPresent() && blockOptional.get().isInstanceOf[NodeProvider]) {
			val nodeProvider = blockOptional.get().asInstanceOf[NodeProvider]

			return nodeProvider
				.getNodes(Direction.UNKNOWN)
				.collect { case n: NodeRedstone => n }
				.toSet
		}

		return Set.empty
	}
}
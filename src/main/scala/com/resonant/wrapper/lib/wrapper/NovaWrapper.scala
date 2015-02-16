package com.resonant.wrapper.lib.wrapper

import nova.core.network.Packet

/**
 * Wraps NOVA objects and provides them with Scala synthetic sugar coating.
 * @author Calclavia
 */
object NovaWrapper {

	implicit class PacketWrapper(underlying: Packet) {

		//Write
		def <<(data: Any) = underlying.write(data)

		//Read
		def >>(data: Any) {
			//TODO: Implicitly find the class type
		}
	}

}
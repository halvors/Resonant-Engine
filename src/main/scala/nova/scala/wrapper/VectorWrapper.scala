package nova.scala.wrapper

import nova.core.util.math.VectorUtil
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

/**
 * @author Calclavia
 */
object VectorWrapper {

	implicit class Vector3DWrapped(underlying: Vector3D) {
		def x = underlying.getX

		def y = underlying.getY

		def z = underlying.getZ

		def xf = underlying.x.toFloat

		def yf = underlying.y.toFloat

		def zf = underlying.z.toFloat

		def xi = underlying.x.toInt

		def yi = underlying.y.toInt

		def zi = underlying.z.toInt

		def +(other: Vector3D) = underlying.add(other)

		def +(other: Double) = underlying.add(VectorUtil.ONE * other)

		def -(other: Vector3D) = underlying.subtract(other)

		def -(other: Double) = underlying.subtract(VectorUtil.ONE * other)

		def unary_- = underlying.negate()

		def unary_/(other: Double) = VectorUtil.reciprocal(underlying).scalarMultiply(other)

		def *(other: Double) = underlying.scalarMultiply(other)
	}

}

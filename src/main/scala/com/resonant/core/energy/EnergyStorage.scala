package com.resonant.core.energy

import nova.core.component.Component

/**
 * A node used to store energy.
 * @author Calclavia
 */
//TODO: Move to Energy API (if decided), change to component
class EnergyStorage(val newMax: Double = 0) extends Component with Stat[Double] {

	max = newMax

	override protected[this] implicit def n: Numeric[Double] =  Numeric.DoubleIsFractional
}
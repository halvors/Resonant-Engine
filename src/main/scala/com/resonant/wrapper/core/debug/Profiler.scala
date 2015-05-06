package com.resonant.wrapper.core.debug

/**
 * @author Calclavia
 */
class Profiler(val name: String) {
	var time = System.currentTimeMillis()
	var lapped = Seq.empty[Long]

	def delta = System.currentTimeMillis() - time

	def lap() {
		lapped :+= delta
		time = System.currentTimeMillis()
	}

	override def toString = name + " took " + ((System.currentTimeMillis() - time) / 1000d) + " seconds"

	def average = lapped.map(_ / 1000d).sum / lapped.size

	def printAverage(): Unit = {
		println(name + " took " + average + " seconds on average for " + lapped.size + " trials.")
	}
}
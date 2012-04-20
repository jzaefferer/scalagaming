package de.bassistance.catman

import java.awt.Rectangle

class Burger(val px: Int, val py: Int) extends Rectangle(px, py, 26, 26) {
	var visible = true
	def eat {
	  visible = false
	}
}

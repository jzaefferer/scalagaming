package de.bassistance.catman

import java.awt.Rectangle

class Burger(val px: Int, val py: Int) extends Rectangle(px, py, 7, 7) {
	var visible = true
	def eat {
	  visible = false
	}
}

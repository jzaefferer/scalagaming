package de.bassistance.catman

import java.awt.{Dimension, Graphics}
import java.awt.geom.Point2D.{Double => Point}

class Cat(val dimensions: Dimension) {
  
  val player = new Player(12)

  def move {
	  player.move
  }
  
  def moveTo(to: (Int, Int)) {
    player.moveTo(to)
  }
  
  def draw(graphics: Graphics) {
    graphics.drawOval(player.x, player.y, player.size, player.size)
  }
  
}

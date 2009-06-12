package de.bassistance.catman

import java.awt.{Dimension, Graphics, Rectangle}
import java.awt.geom.Point2D.{Double => Point}

class Dog(val dimensions: Dimension, field: Field, cat: Cat) extends Movable(1000, 800) {

  speed = 0.1
  
  var ateCat = false
  
  def size = 15
  
  def acceleration = 1.05
  def maxSpeed = 2
  
  def distanceToCat = current.distance(cat.current)
 
  def move {
	angle = Math.atan2(cat.current.y - current.y, cat.current.x - current.x)
    
	if (distanceToCat < size/2) {
		speed = 0
		ateCat = true
		return
	}
	update
  
	field.checkObstacles(this)
  }  
  
  override def reset {
    super.reset
    speed = 0.1
    ateCat = false
  }

  def draw(graphics: Graphics) {
    graphics.fillOval(x, y, size, size)
  }
}

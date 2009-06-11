package de.bassistance.catman

import java.awt.{Dimension, Graphics, Rectangle}
import java.awt.geom.Point2D.{Double => Point}

class Dog(val dimensions: Dimension, field: Field, cat: Cat) {

  def size = 15
  
  def draw(graphics: Graphics) {
    graphics.fillOval(x, y, size, size)
  }
  
   var current: Point = new Point(1000, 800)
	var speed = 0.1;
    var angle: Double = 0;
	var acceleration = 1.05
	var maxSpeed = 2
  
 def distanceToCat = current.distance(cat.current)
 
  def move {
		angle = Math.atan2(cat.current.y - current.y, cat.current.x - current.x)
    
    if (distanceToCat < size/2) {
		 speed = 0
		 return
    }
		speed *= acceleration
		speed = Math.min(maxSpeed, speed)
		current.x += speed * Math.cos(angle)
		current.y += speed * Math.sin(angle)
  
		field.check(this)
  }
  
  def pushBack(obstacle: Rectangle) {
	  if (current.x + size >= obstacle.x && current.x <= obstacle.x + obstacle.width) {
	    if (current.x < obstacle.x + obstacle.width/2) {
	      current.x -= speed
	    } else {
	      current.x += speed
	    }
	  }
	  if (current.y + size >= obstacle.y && current.y <= obstacle.y + obstacle.height) {
	    if (current.y < obstacle.y + obstacle.height/2) {
	      current.y -= speed
	    } else {
	      current.y += speed
	    }
	  }
	}
  
  def boundingbox = new Rectangle(x, y, size, size)
 
	def x = current.x.asInstanceOf[Int]
	def y = current.y.asInstanceOf[Int]
  
}

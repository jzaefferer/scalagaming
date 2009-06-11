package de.bassistance.catman

import java.awt.{Dimension, Graphics, Rectangle}
import java.awt.geom.Point2D.{Double => Point}

class Cat(val dimensions: Dimension, val field: Field) {
  
  var eaten = 0
  
  def size = 12

  def draw(graphics: Graphics) {
    graphics.drawOval(x, y, size, size)
  }
  
  var current: Point = new Point(100, 100)
	var previous = current
	var target: Point = current
	var speed, angle: Double = 0;
	var distance, distanceTravelled: Double = 0
	var acceleration = 1.05
	var maxSpeed = 3
   
	def move {
		if (distanceTravelled > distance) {
		 speed = 0
		 return
		}
		speed *= acceleration
		speed = Math.min(maxSpeed, speed)
		current.x += speed * Math.cos(angle)
		current.y += speed * Math.sin(angle)
		distanceTravelled = previous.distance(current)
  
		field.check(this)
	}
   
	def moveTo(to: (Int, Int)) {
		previous = new Point(current.x, current.y)
		target = new Point(to._1 - size / 2, to._2 - size / 2)
		angle = Math.atan2(target.y - current.y, target.x - current.x)
		distance = previous.distance(target)
		distanceTravelled = 0
		speed = 1
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
 
	def eat(burger: Burger) {
	  eaten += 1
	  burger.eat
	}
 
	def boundingbox = new Rectangle(x, y, size, size)
 
	def x = current.x.asInstanceOf[Int]
	def y = current.y.asInstanceOf[Int]
  
}

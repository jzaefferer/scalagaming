package de.bassistance.catman

import java.awt.{Dimension, Graphics}
import java.awt.geom.Point2D.{Double => Point}

class Cat(val dimensions: Dimension) {
  
  var current: Point = new Point(100, 100)
  var previous = current
  var target: Point = current
  var speed, angle: Double = 0;
  var distance, distanceTravelled: Double = 0
  var acceleration = 1.05
  var maxSpeed = 10

  def size = 12
  
  def move {
	  if (distanceTravelled > distance) {
		  speed = 0
		  return
	  }
   	  speed *= acceleration
      speed = Math.min(3, speed)
	  current.x += speed * Math.cos(angle)
	  current.y += speed * Math.sin(angle)
	  distanceTravelled = previous.distance(current)
  }
  
  def moveTo(to: (Int, Int)) {
    previous = new Point(current.x, current.y)
    target = new Point(to._1 - size / 2, to._2 - size / 2)
  	angle = Math.atan2(target.y - current.y, target.x - current.x)
  	distance = previous.distance(target)
  	distanceTravelled = 0
  	speed = 1
  }
  
  def draw(graphics: Graphics) {
    graphics.drawOval(current.x.asInstanceOf[Int], current.y.asInstanceOf[Int], size, size)
  }
  
}

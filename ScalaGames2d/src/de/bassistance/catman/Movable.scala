package de.bassistance.catman

import java.awt.{Dimension, Graphics, Rectangle}
import java.awt.geom.Point2D.{Double => Point}

abstract class Movable(val startX: Int, val startY: Int) {

  var angle: Double = 0;
  var speed: Double = 0;
  def size: Int
  def acceleration: Double
  def maxSpeed: Int
  
  var current: Point = new Point(startX, startY)
  
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
  
  def update {
    speed *= acceleration
	speed = Math.min(maxSpeed, speed)
	current.x += speed * Math.cos(angle)
	current.y += speed * Math.sin(angle)
  }
  
  def boundingbox = new Rectangle(x, y, size, size)
 
  def x = current.x.asInstanceOf[Int]
  def y = current.y.asInstanceOf[Int]
  
}

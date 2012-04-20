package de.bassistance.catman

import java.awt.{Dimension, Graphics2D, Rectangle}
import java.awt.geom.Point2D.{Double => Point}
import javax.imageio.ImageIO
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp

class Cat(val dimensions: Dimension, val field: Field) extends Movable(100, 100) {
  
  var eaten = 0
  
  def size = 48
  
  val img = ImageIO.read(getClass().getResource("cat.gif"))

  def draw(graphics: Graphics2D) {
    val rotate = new AffineTransform()
    rotate.rotate(angle, size/2, size/2)
    graphics.drawImage(img, new AffineTransformOp(rotate, AffineTransformOp.TYPE_BICUBIC), x, y)
  }
  
  var previous = current
  var target: Point = current
  var distance, distanceTravelled: Double = 0
  def acceleration = 1.05
  def maxSpeed = 3
   
  def move {
    if (distanceTravelled > distance) {
    	speed = 0
    	return
	}
    update
	distanceTravelled = previous.distance(current)
  
	field.checkBurgers(this)
	field.checkObstacles(this)
  }
   
  def moveTo(to: (Int, Int)) {
	previous = new Point(current.x, current.y)
	target = new Point(to._1 - size / 2, to._2 - size / 2)
	angle = Math.atan2(target.y - current.y, target.x - current.x)
	distance = previous.distance(target)
	distanceTravelled = 0
	speed = 1
  }
 
  def eat(burger: Burger) {
	eaten += 1
	burger.eat
  }
  
  override def reset {
    super.reset
    eaten = 0
  }
 
}

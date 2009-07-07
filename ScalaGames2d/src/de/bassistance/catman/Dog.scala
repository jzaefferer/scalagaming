package de.bassistance.catman

import java.awt.{Dimension, Graphics2D, Rectangle, RenderingHints}
import java.awt.geom.AffineTransform
import java.awt.geom.Point2D.{Double => Point}
import java.awt.image.AffineTransformOp
import javax.imageio.ImageIO

class Dog(val dimensions: Dimension, field: Field, cat: Cat) extends Movable(1000, 800) {

  speed = 0.1
  
  var ateCat = false
  
  def size = 48
  
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
  
  val img = ImageIO.read(getClass().getResource("dog.png"))

  def draw(graphics: Graphics2D) {
    val rotate = new AffineTransform()
    rotate.rotate(angle, size/2, size/2)
    graphics.drawImage(img, new AffineTransformOp(rotate, AffineTransformOp.TYPE_BICUBIC), x, y)
  }
}

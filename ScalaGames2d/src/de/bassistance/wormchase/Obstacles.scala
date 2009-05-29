package de.bassistance.wormchase

import java.awt.{Rectangle, Graphics, Color, Point}

class Obstacles {
  
  var boxes = List[Rectangle]()
  
  def add(at: (Int, Int)) {
    boxes = boxes ::: List(new Rectangle(at._1, at._2, 12, 12))
  }
  
  def hits(target: Point, size: Int): Boolean = {
    for (box <- boxes) {
    	if (box.intersects(new Rectangle(target.x, target.y, size, size))) 
    		return true
    }
    false
  }
  
  def size = boxes.size
  
  def draw(dgb: Graphics) {
    dgb.setColor(Color.blue)
    for (box <- boxes) {
      dgb.fillRect( box.x, box.y, box.width, box.height)
    }
  }
  
  def clear {
    boxes = List()
  }
}

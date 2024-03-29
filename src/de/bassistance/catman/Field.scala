package de.bassistance.catman

import java.awt.{Rectangle, Graphics2D, Dimension}
import javax.imageio.ImageIO

class Field(val dimensions: Dimension) {
  
  val burgers = List(new Burger(500, 500), new Burger(1200, 200), new Burger(250, 750), new Burger(1300, 800))
  val obstructions = List(new Rectangle(152, 50, 75, 120),
                          new Rectangle(500, 120, 400, 204),
                          new Rectangle(550, 400, 50, 150),
                          new Rectangle(800, 600, 200, 200))

  def checkBurgers(cat: Cat) {
    burgers.foreach((burger: Burger) => {
    	if (burger.visible && burger.intersects(cat.boundingbox)) {
    	  cat.eat(burger)
    	}
    })
  }
  
  def burgersLeft = burgers.exists(_.visible)
  
  def checkObstacles(movable: Movable) {
    obstructions.foreach((rec: Rectangle) => {
    	if (rec.intersects(movable.boundingbox)) {
    	  movable.pushBack(rec)
    	}
    })
  }
  
  val burgerImage = ImageIO.read(getClass().getResource("cheeseburger.gif"))
  
  def draw(graphics: Graphics2D) {
    burgers.filter(_.visible).foreach((rec: Rectangle) => {
    	graphics.drawImage(burgerImage, rec.x, rec.y, null)
    })
    obstructions.foreach((rec: Rectangle) => {
    	graphics.drawRect(rec.x, rec.y, rec.width, rec.height)
    })
  }
  
  def reset {
    burgers.foreach(_.visible = true)
  }
  
}

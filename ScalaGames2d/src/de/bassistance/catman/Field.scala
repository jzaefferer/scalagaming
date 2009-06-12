package de.bassistance.catman

import java.awt.{Rectangle, Graphics, Dimension}

class Field(val dimensions: Dimension) {
  
  val burgers = List(new Burger(500, 500), new Burger(1200, 200), new Burger(250, 750), new Burger(1300, 800))
  val obstructions = List(new Rectangle(0, 0, 10, dimensions.height),
                          new Rectangle(10, 0, dimensions.width, 10),
                          new Rectangle(dimensions.width - 10, 0, 10, dimensions.height),
                          new Rectangle(10, dimensions.height - 10, dimensions.width - 10, dimensions.height),
                          new Rectangle(152, 50, 75, 120),
                          new Rectangle(500, 120, 400, 204),
                          new Rectangle(550, 400, 50, 150),
                          new Rectangle(800, 600, 200, 200))

  def checkBurgers(cat: Cat) {
    burgers.filter(_.visible).foreach((burger: Burger) => {
    	if (burger.intersects(cat.boundingbox)) {
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
  
  def draw(graphics: Graphics) {
    burgers.filter(_.visible).foreach((rec: Rectangle) => {
    	graphics.fillOval(rec.x, rec.y, rec.width, rec.height)
    })
    obstructions.foreach((rec: Rectangle) => {
    	graphics.drawRect(rec.x, rec.y, rec.width, rec.height)
    })
  }
  
  def reset {
    burgers.foreach(_.visible = true)
  }
  
}

package de.bassistance.catman

import java.awt.{Color, Graphics, Dimension}

import de.bassistance.framework.Game

class CatmanGame extends Game {

  var dimensions: Dimension = null
  
  lazy val field = new Field(dimensions)
  lazy val cat = new Cat(dimensions, field)
  lazy val dog = new Dog(dimensions, field, cat)
  
  def title = "Catman"
  
  def render(graphics: Graphics) {
    graphics.setColor(Color.black)
    graphics.fillRect(0, 0, dimensions.width, dimensions.height)
    graphics.setColor(Color.white)
    graphics.drawString(title + ", Burgers eaten: " + cat.eaten, 20, 30)
    cat.draw(graphics)
    dog.draw(graphics)
    field.draw(graphics)
  }
  
  def update {
    cat.move
    dog.move
  }
  
  def setDimensions(dimensions: Dimension) {
    this.dimensions = dimensions
  }
  
  def click(at: (Int, Int)) {
    cat.moveTo(at)
  }
  
}

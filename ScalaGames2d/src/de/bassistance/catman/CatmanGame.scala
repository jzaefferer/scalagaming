package de.bassistance.catman

import java.awt.{Color, Graphics, Dimension}

import de.bassistance.framework.Game

class CatmanGame extends Game {

  var dimensions: Dimension = null
  
  lazy val cat = new Cat(dimensions)
  
  def title = "Catman"
  
  def render(graphics: Graphics) {
    graphics.setColor(Color.black)
    graphics.fillRect(0, 0, dimensions.width, dimensions.height)
    graphics.setColor(Color.white)
    graphics.drawString(title, 10, 20)
    cat.draw(graphics)
  }
  
  def update {
    cat.move
  }
  
  def setDimensions(dimensions: Dimension) {
    this.dimensions = dimensions
  }
  
  def click(at: (Int, Int)) {
    cat.moveTo(at)
  }
  
}

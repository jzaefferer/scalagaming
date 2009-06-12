package de.bassistance.catman

import java.awt.{Color, Graphics, Dimension}

import de.bassistance.framework.Game

class CatmanGame extends Game {

  var dimensions: Dimension = null
  
  lazy val field = new Field(dimensions)
  lazy val cat = new Cat(dimensions, field)
  lazy val dog = new Dog(dimensions, field, cat)
  
  @volatile
  var gameOver = false
  
  def title = "Catman"
  
  def render(graphics: Graphics) {
    graphics.setColor(Color.black)
    graphics.fillRect(0, 0, dimensions.width, dimensions.height)
    graphics.setColor(Color.white)
    graphics.drawString(title + ", Burgers eaten: " + cat.eaten + "/" + field.burgers.size, 20, 30)
    cat.draw(graphics)
    dog.draw(graphics)
    field.draw(graphics)
    if (gameOver) {
      graphics.drawString("Game Over", dimensions.width/2 - 100, dimensions.height/2-20)
    }
  }
  
  def update {
    if (gameOver)
    	return
    cat.move
    dog.move
    if (!field.burgersLeft || dog.ateCat) {
      gameOver = true
    }
  }
  
  def setDimensions(dimensions: Dimension) {
    this.dimensions = dimensions
  }
  
  def click(at: (Int, Int)) {
    if (gameOver) {
      cat.reset
      dog.reset
      field.reset
      gameOver = false
      return
    }
    cat.moveTo(at)
  }
  
}

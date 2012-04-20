package de.bassistance.wormchase

import de.bassistance.framework.Game
import java.awt.{Color, Graphics2D, Dimension}
import scala.collection.immutable.TreeSet

class WormGame extends Game {
  
  // TODO replace TreeSet with sorted list, duplicates are allowed!
  var highscores = new TreeSet[Int]

  var dimensions: Dimension = null
  
  @volatile
  var gameOver = false
  
  val obstacles = new Obstacles
  lazy val worm = new Worm(dimensions, obstacles)
  
  def title = "Worm Chase"
  
  def setDimensions(dimensions: Dimension) {
    this.dimensions = dimensions
  }
  
  def click(at: (Int, Int)) {
    if (!gameOver) {
      if (worm.nearHead(at)) {
        gameOver = true
      } else {
        if (!worm.touched(at)) {
          obstacles.add(at)
        }
      }
    } else {
      // restart
      obstacles.clear
      worm.reset
      gameOver = false
    }
  }
  
  def gameOverMessage(dgb: Graphics2D) {
    dgb.setColor(Color.black)
    val score = 100 - obstacles.size
    highscores = highscores + score
    dgb.drawString("Game Over. Your score: " + score, 190, 100);
    var top = 1;
    for (highscore <- highscores.toArray.reverse) {
      dgb.drawString(top + ". " + highscore, 190, 100 + top * 10);
      top += 1
    }
  }
  
  def update {
    if (!gameOver) {
      worm.move
    }
  }
  
  def render(dgb: Graphics2D) {
    dgb.setColor(Color.white)
    dgb.fillRect(0, 0, dimensions.width, dimensions.height)
    dgb.setColor(Color.black)
    obstacles.draw(dgb)
    worm.draw(dgb)
    if (gameOver) {
      gameOverMessage(dgb)
    }
  }
  
}

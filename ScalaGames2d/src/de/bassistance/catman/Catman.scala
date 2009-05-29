package de.bassistance.catman

import de.bassistance.framework.Engine

object Pacman {

  def main(args : Array[String]) : Unit = {
    new Engine(160, new CatmanGame)
  }
  
}

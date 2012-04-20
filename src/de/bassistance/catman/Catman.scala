package de.bassistance.catman

import de.bassistance.framework.Engine

object Catman {

  def main(args : Array[String]) : Unit = {
    new Engine(160, new CatmanGame)
  }
  
}

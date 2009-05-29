package de.bassistance.wormchase;

import de.bassistance.framework.Engine

object WormChase {
  
  def main(args : Array[String]) : Unit = {
    new Engine(20, new WormGame)
  }
}

package de.bassistance.framework

import java.awt.{Graphics, Dimension}

trait Game {

  def setDimensions(dimensions: Dimension)
  def click(at: (Int, Int))
  def update
  def render(context: Graphics)
  def title: String
  
}

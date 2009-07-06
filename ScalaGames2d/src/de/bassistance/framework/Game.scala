package de.bassistance.framework

import java.awt.{Graphics2D, Dimension}

trait Game {

  def setDimensions(dimensions: Dimension)
  def click(at: (Int, Int))
  def update
  def render(context: Graphics2D)
  def title: String
  
}

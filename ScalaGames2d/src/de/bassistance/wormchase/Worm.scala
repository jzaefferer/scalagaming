package de.bassistance.wormchase

import java.awt.{Graphics2D, Point, Color, Dimension}

class Worm(val dimensions: Dimension, val obstacles: Obstacles) {
  
  case class Dir(val x: Double, val y: Double)
  case object N extends Dir(0.0, -1.0)
  case object NE extends Dir(0.7, -0.7)
  case object E extends Dir(1.0, 0.0)
  case object SE extends Dir(0.7, 0.7)
  case object S extends Dir(0.0, 1.0)
  case object SW extends Dir(-0.7, 0.7)
  case object W extends Dir(-1.0, 0.0)
  case object NW extends Dir(-0.7, -0.7)
  val DIRECTIONS = List(N, NE , E, SE, S, SW, W, NW)
  
  val MAXPOINTS = 40
  val DOTSIZE = 12
  val RADIUS = DOTSIZE / 2
  
  var points = 0
  var head, tail = -1
  val cells = Array.make(MAXPOINTS, new Point(0, 0))
  var currentCompass = 0

  def nearHead(at: (Int, Int)) = {
    if (points == 0)
      false
    else
      (Math.abs( cells(head).x + RADIUS - at._1) <= DOTSIZE) &&
      (Math.abs( cells(head).y + RADIUS - at._2) <= DOTSIZE)
  }
  
  def touched(at: (Int, Int)): Boolean = {
    var i = tail
    while (i != head) {
      if( (Math.abs( cells(i).x + RADIUS - at._1) <= RADIUS) &&
          (Math.abs( cells(i).y + RADIUS - at._2) <= RADIUS) )
        return true
      i = (i+1) % MAXPOINTS
    }
    false
  }
  
  def move {
    val previousHead = head
    head = (head + 1) % MAXPOINTS

    if (points == 0) {
      tail = head
      currentCompass = (Math.random * DIRECTIONS.size).asInstanceOf[Int]
      cells(head) = new Point( dimensions.width /2, dimensions.height /2 )
      points += 1
    }
    else if (points == MAXPOINTS) {
      tail = (tail + 1) % MAXPOINTS
      newHead(previousHead)
    }
    else {
      newHead(previousHead)
      points += 1
    }
  }
  
  def newHead(previousPosition: Int) {
    // Get a new position based on a semi-random
    // variation of the current position.
	var newBearing: Int = varyBearing
    var newPt: Point = nextPoint(previousPosition, newBearing )
    // offsets to avoid an obstacle

    if (obstacles.hits(newPt, DOTSIZE)) {
      var found: (Int, Point) = findAlternative(previousPosition)
      newBearing = found._1
      newPt = found._2
    }
    // new head position
    cells(head) = newPt
    // new compass direction
    currentCompass = newBearing
  }
  
  def findAlternative(previousPosition: Int): (Int, Point) = {
	var fixedOffsets = Array(-2, 2, -4)
    for (offset <- fixedOffsets) {
      val newBearing = calculateBearing(offset)
      val newPt = nextPoint(previousPosition, newBearing)
      if (!obstacles.hits(newPt, DOTSIZE))
        return (newBearing, newPt)
    }
    throw new IllegalStateException("can't find alternative")
  }
  
  val offsets = Array(0, 0, 0, 1, 1, -1, -1, 2, -2)
  def varyBearing = calculateBearing( offsets((Math.random * offsets.size ).asInstanceOf[Int]) )
  
  def nextPoint(previousPosition: Int, bearing: Int) = {
    val dir = DIRECTIONS(bearing)

    var newX = cells(previousPosition).x + (DOTSIZE * dir.x).asInstanceOf[Int]
    var newY = cells(previousPosition).y + (DOTSIZE * dir.y).asInstanceOf[Int]

    // modify newX/newY if < 0, or > pWidth/pHeight use wraparound 
    if (newX+DOTSIZE < 0)     // is circle off the left edge of the canvas?
      newX = newX + dimensions.width
    else  if (newX > dimensions.width)  // is circle off the right edge of the canvas?
      newX = newX - dimensions.width  

    if (newY+DOTSIZE < 0)     // is circle off the top of the canvas?
      newY = newY + dimensions.height
    else  if (newY > dimensions.height) // is circle off the bottom of the canvas?
      newY = newY - dimensions.height

    new Point(newX,newY)
  }
  
  def calculateBearing(offset: Int): Int = {
    val turn = currentCompass + offset
    // ensure that turn is between N to NW (0 to 7)
    if (turn >= DIRECTIONS.size)
      return turn - DIRECTIONS.size
    else if (turn < 0)
      return DIRECTIONS.size + turn
    return turn
  }
  
  def draw(dgb: Graphics2D) {
    if (points == 0)
      return
        
    dgb.setColor(Color.black);
    var i = tail;
    while (i != head) {
    	dgb.fillOval(cells(i).x, cells(i).y, DOTSIZE, DOTSIZE);
        i = (i+1) % MAXPOINTS;
    }
    dgb.setColor(Color.red);
    dgb.fillOval( cells(head).x, cells(head).y, DOTSIZE, DOTSIZE);
  }
  
  def reset {
    points = 0
    head = -1
    tail = -1
  }
  
}

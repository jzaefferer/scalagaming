package de.bassistance.framework

import javax.swing.JFrame
import java.awt.{Color, Toolkit, GraphicsEnvironment, GraphicsDevice, Graphics2D}
import java.awt.event.{WindowEvent, KeyAdapter, KeyEvent, MouseAdapter, MouseEvent}
import java.awt.image.BufferStrategy

/**
 * Base class for a simple 2D game. Handles active fullscreen rendering
 * with seperated update cycles for game logic and rendering.
 */
class Engine(val speed: Int, val game: Game) extends JFrame {
  
  @volatile
  var running = false
  
  val dimensions = Toolkit.getDefaultToolkit.getScreenSize
  
  val drawingPeriod = (1000/speed) * 1000000L
  
  var bStrategy: BufferStrategy = null
  
  var noDelays: Int = 0
  val NO_DELAYS_PER_YIELD = 16
  val MAX_FRAME_SKIPS = 5
  
  val animator = new Thread(new Runnable {
	  def run {
		var beforeTime: Long = 0
		var afterTime: Long = 0
		var timeDiff: Long = 0
		var sleepTime: Long = 0
	  	var overSleepTime = 0L
	  	var noDelays = 0
	  	var excess = 0L

	  	beforeTime = System.nanoTime

	  	running = true

	  	while(running) {
	  		game.update
	  		paintScreen

	  		afterTime = System.nanoTime
	  		timeDiff = afterTime - beforeTime
	  		sleepTime = (drawingPeriod - timeDiff) - overSleepTime  

	  		if (sleepTime > 0) {   // some time left in this cycle
	  			try {
	  				Thread.sleep(sleepTime/1000000L)  // nano -> ms
	  			} catch {
	  			case e: InterruptedException =>
	  			}
	  			overSleepTime = (System.nanoTime - afterTime) - sleepTime
	  		}
	  		else {    // sleepTime <= 0 the frame took longer than the drawingPeriod
	  			excess -= sleepTime  // store excess time value
	  			overSleepTime = 0L

	  			noDelays += 1;
	  			if (noDelays >= NO_DELAYS_PER_YIELD) {
	  				//Thread.yield()   // give another thread a chance to run
	  				noDelays = 0
	  			}
	  		}

	  		beforeTime = System.nanoTime

	  		/* If frame animation is taking too long, update the game state
	  		without rendering it, to get the updates/sec nearer to
	  		the required FPS. */
	  		var skips = 0
	  		while((excess > drawingPeriod) && (skips < MAX_FRAME_SKIPS)) {
	  			excess -= drawingPeriod
	  			// update state but don't render
	  			game.update    
	  			skips += 1
	  		}
//	  		if (skips > 0)
//	  			println("skipped " + skips + " frames")
	  	}
	  }
  })

  {
    setTitle(game.title)
	setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE )
 
	setPreferredSize(dimensions)
   	game.setDimensions(dimensions)
    
	setUndecorated(true)
	setIgnoreRepaint(true)
	pack
	setResizable(false)
 
	var device = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice
	createBufferStrategy(2)
	bStrategy = getBufferStrategy
 
	animator.start
 
	setFocusable(true)
	requestFocus
	addKeyListener(new KeyAdapter() {
		override def keyPressed(event: KeyEvent) {
		  if (event.getKeyChar == KeyEvent.VK_ESCAPE) {
		    stop
		  }
		  // toggle fullscreen on alt+anter, if available
		  if (event.getKeyChar == KeyEvent.VK_ENTER && event.isAltDown) {
		    toggleFullScreen(device)
		  }
		}
	})
 
	setBackground(Color.white)
	addMouseListener(new MouseAdapter() {
		override def mousePressed(e: MouseEvent) {
			game.click((e.getX, e.getY))
		}
	})
  
  	setVisible(true)
   }
  
   def stop {
     running = false
     dispatchEvent(new WindowEvent(Engine.this, WindowEvent.WINDOW_CLOSING))
   }
  
   def toggleFullScreen(device: GraphicsDevice) {
	  if (device.isFullScreenSupported) {
	   	if (device.getFullScreenWindow == this) {
	   		device.setFullScreenWindow(null)
	   	} else {
	   		device.setFullScreenWindow(this)
	   	}
	  }
   }
  
   def paintScreen {
	  var graphics = bStrategy.getDrawGraphics.asInstanceOf[Graphics2D]
	  game.render(graphics)
	  graphics.dispose
	  if(!bStrategy.contentsLost) {
	    bStrategy.show
	  }
	  Toolkit.getDefaultToolkit.sync
   }
}

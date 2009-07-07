package de.bassistance.framework

import scala.collection.mutable.{Map, HashMap}
import javazoom.jl.player.Player
import java.io.InputStream
import java.util.concurrent.Executors

class Sounds {

  implicit def toRunnable(fn: () => Unit) = new Runnable() { def run() { fn() } }
  
  val pool = Executors.newCachedThreadPool
  
  val loaded = new HashMap[String, Player]
  
  def play(name: String, stream: InputStream) {
	loaded(name) = new Player(stream)
    pool.submit(() => loaded(name).play)
  }
  
  def stop(name: String) {
    loaded(name).close
  }
  
}

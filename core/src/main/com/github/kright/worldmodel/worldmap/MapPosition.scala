package com.github.kright.worldmodel.worldmap

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * MapPosition always valid!
  *
  * Position may have coordinates like -1, 100500 :
  * Coordinates will be wrapped to map size if map allows wrapping
  */
trait MapPosition extends Position {
  def x: Int

  def y: Int

  /* position always valid */
  override def isValid(implicit topology: MapTopology): Boolean = true

  /* return itself */
  override def asMapPosition(implicit topology: MapTopology): MapPosition = this
}

private class MapPositionImpl(val x: Int, val y: Int) extends MapPosition

trait Position {
  def x: Int

  def y: Int

  def isValid(implicit topology: MapTopology): Boolean = topology.isValid(this)

  def asMapPosition(implicit topology: MapTopology): MapPosition = {
    assert(isValid)
    new MapPositionImpl(topology.wrappedX(this), topology.wrappedY(this))
  }
}

/*
 *     Copyright 2018 Igor Slobodskov
 *
 *     This file is part of Simplicivy.
 *
 *     Simplicivy is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Simplicivy is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Simplicivy.  If not, see <http://www.gnu.org/licenses/>.
 */

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

  def distanceTo(p: MapPosition)(implicit topology: MapTopology): Int =
    topology.distance(x, y, p.x, p.y)
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

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

import com.github.kright.utils.Array2d
import com.github.kright.worldmodel.{MapCell, MutableMapCell, UnknownMapCell}

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * Base map view. Only ground truth values, this map doesn't content unknown and shadowed cells
  */
class SimpleMap(implicit val topology: MapTopology) extends MapView[MutableMapCell] {

  private val table = new Array2d[MutableMapCell](topology.width, topology.height)

  override def apply(p: MapPosition): MutableMapCell = table(p.x, p.y)

  override def apply(validX: Int, validY: Int): MutableMapCell = {
    assert(topology.isValid(validX, validY))
    table(topology.wrappedX(validX, validY), topology.wrappedY(validY))
  }

  override def allCells: Seq[MutableMapCell] = table.array.view

  def update(p: MapPosition, value: MutableMapCell): Unit = table(p.x, p.y) = value

  def makePlayerView(): PlayerMapView = {
    val array2d = new Array2d[MapCell](topology.width, topology.height)

    for (y <- 0 until topology.height; x <- 0 until topology.width)
      array2d(x, y) = new UnknownMapCell(x, y)

    new PlayerMapView(this, array2d)
  }
}

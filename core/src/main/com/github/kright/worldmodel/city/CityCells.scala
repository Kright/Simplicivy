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

package com.github.kright.worldmodel.city

import scala.collection.mutable
import com.github.kright.worldmodel.{MapCell, Visible}

/**
  * Created by Igor Slobodskov on 11 June 2018
  */
class CityCells(city: City) extends mutable.Set[MapCell] {

  private val cells = new mutable.HashSet[MapCell]()

  def cultureForAddingCell(cell: MapCell): Int = {
    implicit val topology = city.owner.map.topology

    //todo add this to game rules
    cell.distanceTo(city.center) match {
      case 2 => 10
      case 3 => 100
      case 4 => 1000
    }
  }

  override def +=(cell: MapCell): CityCells.this.type = {
    assert(cell.visibility == Visible)
    cells += cell
    this
  }

  override def -=(cell: MapCell): CityCells.this.type = {
    assert(cells.contains(cell))
    cells -= cell
    this
  }

  override def contains(cell: MapCell): Boolean = cells.contains(cell)

  override def iterator: Iterator[MapCell] = cells.iterator
}

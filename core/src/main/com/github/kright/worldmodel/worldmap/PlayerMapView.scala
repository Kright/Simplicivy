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
import com.github.kright.worldmodel.{MapCell, Visible}
import com.github.kright.worldmodel.command.{Command, UndoCommand}

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * Player's own map view (it stores cells which he visited)
  */
class PlayerMapView(private val simpleMap: SimpleMap,
                    private val table: Array2d[MapCell]) extends MapView[MapCell] {

  implicit val topology: MapTopology = simpleMap.topology

  assert(table.height == topology.height)
  assert(table.width == topology.width)

  override def apply(p: MapPosition): MapCell = table(p.x, p.y)

  override def apply(validX: Int, validY: Int): MapCell = {
    assert(topology.isValid(validX, validY))
    table(topology.wrappedX(validX, validY), topology.wrappedY(validY))
  }

  override def allCells: Seq[MapCell] = table.array.view

  private def update(p: MapPosition, value: MapCell): Unit = table(p.x, p.y) = value

  /* open cell and return it */
  def openCell(p: MapPosition): MapCell = {
    val opened = simpleMap(p)
    this (p) = opened
    opened
  }

  /* make cell shadowed and return it */
  def shadowCell(p: MapPosition): MapCell = {
    val old = this (p)
    assert(old.visibility.isKnown)
    val shadowed = old.getShadowed()
    this (p) = shadowed
    shadowed
  }

  def openCellCommand(cell: MapPosition): Command = Command { gameWorld =>
    val current = this (cell)
    if (current.visibility == Visible) {
      UndoCommand.empty
    } else {
      openCell(current)
      UndoCommand {
        this (current) = current
        true
      }
    }
  }

  def openCellCommand(cells: MapPosition*): Command = Command { gameWorld =>
    val oldCells = cells.map(apply)
    val openedCells = cells.map(openCell)

    UndoCommand {
      oldCells.foreach { c =>
        this (c) = c
      }
      true
    }
  }
}

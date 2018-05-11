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

import com.github.kright.worldmodel.city.CityView
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.units.GameUnitView
import com.github.kright.worldmodel.{MapCell, Visible}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * view of Map, which allows only observation
  */
trait MapView[+T <: MapCell] {
  implicit val topology: MapTopology

  def apply(p: MapPosition): T

  def apply(p: Position): T = apply(p.asMapPosition)

  def apply(validX: Int, validY: Int): T

  def allCells: Seq[T] //return view of data


  def visibleCells: Seq[T] = allCells.filter(_.visibility == Visible)

  def knownCells: Seq[T] = allCells.filter(_.visibility.isKnown)

  def allCities: Seq[CityView] = knownCells.flatMap(_.city)

  def allUnits: Seq[GameUnitView] = knownCells.flatMap(_.units)

  def cellsWithResources(owner: CountryLink): Seq[T] = knownCells.filter(_.resource.isDefined)

  def playerCells(owner: CountryLink): Seq[T] = knownCells.filter(_.owner.contains(owner))

  def playerUnits(owner: CountryLink): Seq[GameUnitView] = allUnits.filter(_.owner == owner)

  def playerResources(owner: CountryLink): Seq[T] = knownCells.filter(_.resource.isDefined)

  //range 0: one cell, 1: 6 neighbor cells
  def allCellsInRange(x: Int, y: Int, range: Int): Seq[T] = {
    val arr = new ArrayBuffer[T]()

    for (dy <- -range to range; dx <- -range to range) {
      if (topology.isValid(x + dx, y + dy) && topology.distance(x, y, x + dx, y + dy) <= range) {
        arr += this (x + dx, y + dy)
      }
    }

    arr
  }

  def allCellsInRange(center: MapPosition, range: Int): Seq[T] =
    allCellsInRange(center.x, center.y, range)

  def neighbors(c: MapPosition): Seq[T] = neighbors(c.x, c.y)

  def neighbors(x: Int, y: Int): Seq[T] = {
    val arr = new ArrayBuffer[T]()

    def add(dx: Int, dy: Int): Unit = {
      val lx = x + dx
      val ly = y + dy
      if (topology.isValid(lx, ly)) {
        if (topology.distance(x, y, lx, ly) == 1) {
          arr += this (lx, ly)
        }
      }
    }

    add(1, 0)
    add(0, 1)
    add(-1, 0)
    add(0, -1)
    add(1, -1)
    add(-1, 1)

    arr
  }
}

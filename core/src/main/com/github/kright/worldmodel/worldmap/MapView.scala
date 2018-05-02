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

import com.github.kright.worldmodel.city.City
import com.github.kright.worldmodel.units.GameUnit
import com.github.kright.worldmodel.{MapCell, Visible}

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * view of Map, which allows only observation
  */
trait MapView[+T <: MapCell] {
  implicit val topology: MapTopology

  def apply(p: MapPosition): T

  def apply(p: Position): T = apply(p.asMapPosition)

  def allCells: Seq[T] //return view of data

  def openedCells: Seq[T] = allCells.filter(_.visibility == Visible)

  def knownCells: Seq[T] = allCells.filter(_.visibility.isKnown)

  def allCities: Seq[City] = knownCells.flatMap(_.city)

  def allUnits: Seq[GameUnit] = knownCells.flatMap(_.units)
}

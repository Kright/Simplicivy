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

import com.github.kright.worldmodel.{MapCell, Visible}

import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 11 June 2018
  */
class CityCitizens(city: City) {

  val workingCitizens: mutable.Set[MapCell] = new mutable.HashSet[MapCell]()

  var clowns: Int = 0
  var scientists: Int = 0
  var policemen: Int = 0
  var taxCollectors: Int = 0

  def size: Int = workingCitizens.size + inCityCitizensCount

  def inCityCitizensCount: Int = clowns + scientists + policemen + taxCollectors

  def addCitizen(): Unit = ??? //todo

  def removeCitizen(): Unit = ??? //todo

  def calculateProduction(): CityProductionView = new CityProduction() {
    workingCitizens.foreach { cell =>
      assert(cell.visibility == Visible)
      //todo!!
    }

    assert(culture >= 0)
    assert(science >= 0)
    assert(commerce >= 0)
    assert(production > 0)
    //food may be negative
  }
}


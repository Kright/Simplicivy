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

package com.github.kright.worldmodel

import com.github.kright.worldmodel.country.{Country, CountryLink}
import com.github.kright.worldmodel.worldmap.SimpleMap

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 05 May 2018
  */
class GameWorld(val baseMap: SimpleMap) {

  val countries: ArrayBuffer[Country] = new ArrayBuffer[Country]()

  var stepsCount: Int = 0
  var currentCountryNo: Int = 0

  def getNextCountry(): Country = {
    assert(countries.nonEmpty)
    currentCountryNo += 1
    if (currentCountryNo > countries.size) {
      stepsCount += 1
      currentCountryNo = 0
    }
    countries(currentCountryNo)
  }
}

class PlayerEnvironment(private val world: GameWorld, private val country: Country) {

}
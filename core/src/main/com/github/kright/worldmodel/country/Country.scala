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

package com.github.kright.worldmodel.country

import com.github.kright.worldmodel.gamerules.Nation
import com.github.kright.worldmodel.science.PlayerScience
import com.github.kright.worldmodel.worldmap.PlayerMapView

import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 05 May 2018
  */
class Country(val uniqueId: Int,
              val map: PlayerMapView,
              val nation: Nation,
              val science: PlayerScience) extends CountryEquality {

  var exists: Boolean = true // country may be defeated

  val knownCountries = new mutable.HashSet[CountryLink]()

  override protected def asCountry: Country = this

  override def hashCode(): Int = uniqueId
}

//todo add traits for "own country" and "country"

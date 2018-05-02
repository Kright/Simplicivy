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

import com.github.kright.utils.HasId
import com.github.kright.worldmodel.city.City
import com.github.kright.worldmodel.gamerules.GameRules
import com.github.kright.worldmodel.science.PlayerTechnologies
import com.github.kright.worldmodel.units.GameUnit

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * interface for country view
  */
trait CountryLink {
  // may there would be view of current player for this country or something else
}


trait Country extends HasId {

  //different counties may have different units types, buildings and etc.
  def gameRules: GameRules

  def cities: Seq[City]

  def units: Seq[GameUnit]

  def gold: Int

  def culture: Int

  def science: PlayerTechnologies
}

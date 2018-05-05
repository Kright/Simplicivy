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

package com.github.kright.worldmodel.units

import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.GameUnitType
import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  */
trait GameUnitView extends MapPosition {

  //may be edited py player
  def name: String


  def movementPoints: Int

  def attackPoints: Int // some units may attack twice


  def activity: GameUnitActivity

  def hp: Int

  def maxHp: Int

  def unitType: GameUnitType

  def owner: CountryLink
}

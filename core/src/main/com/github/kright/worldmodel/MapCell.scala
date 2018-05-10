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

import com.github.kright.worldmodel.units.GameUnitView
import com.github.kright.worldmodel.worldmap.MapPosition
import com.github.kright.worldmodel.city.CityView
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules._


/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait MapCell extends MapPosition with TerrainParams {

  def visibility: CellVisibility

  def units: Seq[GameUnitView]

  def owner: Option[CountryLink]

  /**
    * @return if shadowed return self else make shadowed copy
    */
  def getShadowed(): MapCell

  def mayContainUnitsOf(player: CountryLink): Boolean = {
    if (city.exists(_.owner != player)) return false
    if (units.exists(_.owner != player)) return false
    true
  }
}


trait TerrainParams {

  def biom: Biom

  def height: Int

  def modifier: Option[TerrainModifier]

  def resource: Option[ResourceType]

  def landUpgrade: Option[LandUpgradeType]

  def city: Option[CityView]

  def road: RoadType

  def hasPollution: Boolean


  def movementCost: Int = biom.movementCost + modifier.map(_.additionalMovementCost).getOrElse(0)

  def isLand: Boolean = biom.isLand

  def isWater: Boolean = !isLand
}

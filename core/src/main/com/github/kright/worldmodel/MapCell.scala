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

import com.github.kright.worldmodel.units.GameUnit
import com.github.kright.worldmodel.worldmap.MapPosition
import com.github.kright.worldmodel.city.City
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.{LandUpgradeType, ResourceType, RoadType, TerrainType}


/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait MapCell extends MapPosition with LandModifiers {

  def visibility: CellVisibility

  def units: Seq[GameUnit]

  def owner: Option[CountryLink]

  /**
    * @return if shadowed return self else make shadowed copy
    */
  def getShadowed(): MapCell
}


trait LandModifiers {
  def terrain: TerrainType

  def resource: Option[ResourceType]

  def road: RoadType

  def landUpgrade: Option[LandUpgradeType]

  def hasPollution: Boolean

  def city: Option[City]
}

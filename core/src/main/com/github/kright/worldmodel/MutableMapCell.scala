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
import com.github.kright.worldmodel.city.City
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
class MutableMapCell(val x: Int, val y: Int) extends MapCell {
  override def visibility: CellVisibility = Visible

  var terrain: TerrainType = _

  var city: Option[City] = None

  var resource: Option[ResourceType] = None

  val units: ArrayBuffer[GameUnit] = new ArrayBuffer[GameUnit]()

  var owner: Option[CountryLink] = None

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = new ShadowedMapCell(this)

  var road: RoadType = NoRoad

  var landUpgrade: Option[LandUpgradeType] = None

  var hasPollution: Boolean = false
}


class ShadowedMapCell private(val x: Int, val y: Int,
                              val terrain: TerrainType,
                              val city: Option[City],
                              val resource: Option[ResourceType],
                              val owner: Option[CountryLink],
                              val road: RoadType,
                              val landUpgrade: Option[LandUpgradeType],
                              val hasPollution: Boolean) extends MapCell {

  def this(c: MapCell) = this(c.x, c.y, c.terrain, c.city, c.resource, c.owner, c.road,
    c.landUpgrade, c.hasPollution)

  override def visibility: CellVisibility = Shadowed

  override def units: Seq[GameUnit] = List.empty

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = this
}


class UnknownMapCell(val x: Int, val y: Int) extends MapCell {
  override def visibility: CellVisibility = Unknown


  private def throwException = throw new RuntimeException("this is Unknown cell!")


  override def terrain: TerrainType = throwException

  override def city: Option[City] = throwException

  override def resource: Option[ResourceType] = throwException

  override def units: Seq[GameUnit] = throwException

  override def owner: Option[CountryLink] = throwException

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = throwException

  override def road: RoadType = throwException

  override def landUpgrade: Option[LandUpgradeType] = throwException

  override def hasPollution: Boolean = throwException
}

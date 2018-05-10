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
import com.github.kright.worldmodel.city.CityView
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
class MutableMapCell(val x: Int, val y: Int) extends MapCell {
  override def visibility: CellVisibility = Visible

  var city: Option[CityView] = None

  var resource: Option[ResourceType] = None

  val units: ArrayBuffer[GameUnitView] = new ArrayBuffer[GameUnitView]()

  var owner: Option[CountryLink] = None

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = new ShadowedMapCell(this)

  var road: RoadType = NoRoad

  var landUpgrade: Option[LandUpgradeType] = None

  var hasPollution: Boolean = false

  var biom: Biom = _

  var modifier: Option[TerrainModifier] = None

  var height: Int = 0
}


class ShadowedMapCell private(val x: Int, val y: Int,
                              val city: Option[CityView],
                              val resource: Option[ResourceType],
                              val owner: Option[CountryLink],
                              val road: RoadType,
                              val landUpgrade: Option[LandUpgradeType],
                              val hasPollution: Boolean,
                              val biom: Biom,
                              val height: Int,
                              val modifier: Option[TerrainModifier]) extends MapCell {

  def this(c: MapCell) = this(c.x, c.y, c.city, c.resource, c.owner, c.road,
    c.landUpgrade, c.hasPollution, c.biom, c.height, c.modifier)

  override def visibility: CellVisibility = Shadowed

  override def units: Seq[GameUnitView] = List.empty

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = this
}


class UnknownMapCell(val x: Int, val y: Int) extends MapCell {
  override def visibility: CellVisibility = Unknown


  private def throwException = throw new RuntimeException("this is Unknown cell!")


  override def city: Option[CityView] = throwException

  override def resource: Option[ResourceType] = throwException

  override def units: Seq[GameUnitView] = throwException

  override def owner: Option[CountryLink] = throwException

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = throwException

  override def road: RoadType = throwException

  override def landUpgrade: Option[LandUpgradeType] = throwException

  override def hasPollution: Boolean = throwException

  override def biom: Biom = throwException

  override def height: Int = throwException

  override def modifier: Option[TerrainModifier] = throwException
}

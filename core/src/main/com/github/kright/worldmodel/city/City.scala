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

import com.github.kright.worldmodel.MapCell
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.{BuildingEffectView, CityBuildingType, ResourceType}
import com.github.kright.worldmodel.worldmap.MapPosition


/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait CityView extends MapPosition {

  def name: String

  def citizensCount: Int

  def totalCulture: Int

  def owner: CountryLink
}


trait CityOwnerView extends CityView {

  def name_=(newName: String)

  def workingCitizens: Seq[MapCell]


  def buildings: Seq[CityBuildingType]

  def hasBuilding(b: CityBuildingType): Boolean

  def buildingEffects: BuildingEffectView

  def resourceCount(resource: ResourceType): Int

  def production: CityProductionView

  def project: CityProject
}

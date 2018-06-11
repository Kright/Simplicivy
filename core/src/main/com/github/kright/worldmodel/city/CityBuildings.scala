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

import com.github.kright.worldmodel.gamerules.{BuildingEffect, BuildingEffectView, CityBuildingType, RequirementForCityProduction}

import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 10 June 2018
  */
class CityBuildings(city: City) {

  def requirementsMet(r: RequirementForCityProduction): Boolean = {
    // todo
    r.cityBuildings.forall(contains) && r.citizensConsumption < city.citizens.size
  }

  private val buildings: mutable.HashSet[CityBuildingType] = new mutable.HashSet[CityBuildingType]()
  private val effectSum: BuildingEffect = new BuildingEffect()


  def allBuildings: Iterable[CityBuildingType] = buildings

  def effect: BuildingEffectView = effectSum


  def contains(b: CityBuildingType) = buildings.contains(b)

  def build(b: CityBuildingType): Unit = {
    assert(!contains(b))
    assert(requirementsMet(b.requires))
    buildings += b

    updateCityBuildings()
  }

  def remove(b: CityBuildingType): Unit = {
    assert(contains(b))
    buildings -= b

    updateCityBuildings()
  }


  protected def updateCityBuildings(): Unit = {
    effectSum.reset()

    buildings.foreach { b =>
      effectSum += b.buildingEffect
    }
  }
}

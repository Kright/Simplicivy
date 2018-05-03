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

package com.github.kright.worldmodel.gamerules

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait CityBuildingType extends HasName {

  def requires: RequirementForCityProduction

  def cost: Int = requires.cost


  def buildingEffect: BuildingEffect
}

class CityBuildingTypeImpl(var name: String,
                           var requires: RequirementForCityProduction,
                           var buildingEffect: BuildingEffect) extends CityBuildingType

trait BuildingEffect {

  def maintenance: Int

  def happiness: Int

  def culture: Int


  def taxBonus: Int

  def researchBonus: Int

  def productionBonus: Int

  def defenceBonus: Int

  def corruptionDecrease: Int


  def pollution: Int
}

class BuildingEffectImpl(var maintenance: Int,
                         var happiness: Int,
                         var culture: Int,
                         var taxBonus: Int,
                         var researchBonus: Int,
                         var productionBonus: Int,
                         var defenceBonus: Int,
                         var corruptionDecrease: Int,
                         var pollution: Int) extends BuildingEffect

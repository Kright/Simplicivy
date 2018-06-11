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

import com.github.kright.utils.DilatedExecutor
import com.typesafe.config.Config

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
class CityBuildingType(val name: String,
                       var requires: RequirementForCityProduction,
                       var buildingEffect: BuildingEffectView) extends HasName


trait BuildingEffectView {

  def maintenance: Int

  def happiness: Int

  def culture: Int


  def taxBonus: Int

  def researchBonus: Int

  def productionBonus: Int

  def defenceBonus: Int

  def corruptionDecrease: Int


  def decreasedFoodToGrowth: Boolean


  def pollution: Int
}


class BuildingEffect(var maintenance: Int = 0,
                     var happiness: Int = 0,
                     var culture: Int = 0,
                     var taxBonus: Int = 0,
                     var researchBonus: Int = 0,
                     var productionBonus: Int = 0,
                     var defenceBonus: Int = 0,
                     var corruptionDecrease: Int = 0,
                     var decreasedFoodToGrowth: Boolean = false,
                     var pollution: Int = 0) extends BuildingEffectView {

  def +=(add: BuildingEffectView): BuildingEffect = {
    maintenance += add.maintenance
    happiness += add.happiness
    culture += add.culture
    taxBonus += add.taxBonus
    researchBonus += add.researchBonus
    productionBonus += add.productionBonus
    defenceBonus += add.defenceBonus
    corruptionDecrease += add.corruptionDecrease
    decreasedFoodToGrowth ||= add.decreasedFoodToGrowth
    pollution += add.pollution

    this
  }

  def reset(): Unit = {
    maintenance = 0
    happiness = 0
    culture = 0
    taxBonus = 0
    researchBonus = 0
    productionBonus = 0
    defenceBonus = 0
    corruptionDecrease = 0
    decreasedFoodToGrowth = false
    pollution = 0
  }
}


object CityBuildingType extends DilatedConverter[CityBuildingType] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): CityBuildingType = {
    new CityBuildingType(config.getString("name"),
      config.asLinked[RequirementForCityProduction]("requires"),
      config.getAs[BuildingEffect]("effects"))
  }
}


object BuildingEffect extends ConfigConverter[BuildingEffect] {
  override def convert(config: Config): BuildingEffect = {
    import ConfigLoader._

    implicit def parseOr0(s: String): Int = if (config.hasPath(s)) config.getInt(s) else 0

    new BuildingEffect(
      "maintenance",
      "happiness",
      "culture",
      "taxBonus",
      "researchBonus",
      "productionBonus",
      "defenceBonus",
      "corruptionDecrease",
      config.getOption[Boolean]("decreasedFoodToGrowth").getOrElse(false),
      "pollution"
    )
  }
}

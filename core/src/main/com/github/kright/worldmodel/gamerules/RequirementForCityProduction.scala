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

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 28 April 2018
  */

class RequirementForCityProduction(var cost: Int,
                                   var citizensConsumption: Int,
                                   var technology: ArrayBuffer[TechnologyDescription],
                                   var resources: ArrayBuffer[ResourceType],
                                   var cityBuildings: ArrayBuffer[CityBuildingType],
                                   var requireRiver: Boolean = false,
                                   var requireSea: Boolean = false)


object RequirementForCityProduction extends DilatedConverter[RequirementForCityProduction] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): RequirementForCityProduction = {
    new RequirementForCityProduction(
      cost = config.getInt("cost"),
      citizensConsumption = config.getOption[Int]("citizens").getOrElse(0),
      technology = new ArrayBuffer[TechnologyDescription](),
      resources = new ArrayBuffer[ResourceType](),
      cityBuildings = new ArrayBuffer[CityBuildingType](),
      requireRiver = config.getOption[Boolean]("requireRiver").getOrElse(false),
      requireSea = config.getOption[Boolean]("requireSea").getOrElse(false)
    ) {
      this.doLate {
        technology ++= config.getStrings("technology").map(gameRules.technologies(_))
        resources ++= config.getStrings("resources").map(gameRules.resources(_))
        cityBuildings ++= config.getStrings("cityBuildings").map(gameRules.cityBuildings(_))
      }
    }
  }
}
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

import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 27 April 2018
  *
  * effectively persistent structure about game rules
  * technologies tree, city buildings, unit types and what needed to play a game
  *
  * Some information may be redundant:
  * for example, Resource contents links to several terrain types (on which them may be placed)
  * and information has to be consistent.
  * resources collection must contain all possible values! And about others: too
  */
trait GameRules {

  // pure classes, don't hold links to other structures
  def technologies: GameRulesHolder[TechnologyDescription]

  def terrainTypes: GameRulesHolder[TerrainType]

  // uses techs and terrain types
  def resources: GameRulesHolder[ResourceType]

  // uses someself, resources and technologies
  def cityBuildings: GameRulesHolder[CityBuildingType]

  // uses terrain types
  def langUpgradeTypes: GameRulesHolder[LandUpgradeType]

  // uses tech, resources, cityBuildingTypes, and itself
  def unitTypes: GameRulesHolder[GameUnitType]

  // uses techs, unitTypes, cityBuildings
  def nations: GameRulesHolder[Nation]
}

class GameRulesHolder[T <: HasName] {
  private val map = new mutable.HashMap[String, T]()
  private val lst = new mutable.ArrayBuffer[T]()

  def apply(name: String): T = map(name)

  def ++=(values: Seq[T]): Unit = {
    lst ++= values
    map ++= values.map(v => (v.name, v))
  }

  def all: Seq[T] = lst
}

class GameRulesImpl() extends GameRules {
  var technologies: GameRulesHolder[TechnologyDescription] = new GameRulesHolder()
  var terrainTypes: GameRulesHolder[TerrainType] = new GameRulesHolder()
  var resources: GameRulesHolder[ResourceType] = new GameRulesHolder()
  var cityBuildings: GameRulesHolder[CityBuildingType] = new GameRulesHolder()
  var langUpgradeTypes: GameRulesHolder[LandUpgradeType] = new GameRulesHolder()
  var unitTypes: GameRulesHolder[GameUnitType] = new GameRulesHolder()
  var nations: GameRulesHolder[Nation] = new GameRulesHolder()
}


object GameRules extends ConfigConverter[GameRulesImpl] {

  import ConfigLoader._
  import scala.collection.JavaConverters._

  override def convert(config: Config): GameRulesImpl = {

    implicit val gameRules: GameRulesImpl = new GameRulesImpl()
    implicit val linking: DilatedExecutor = new DilatedExecutor()

    gameRules.technologies ++= config.getConfigList("technologies").asScala.map(_.asLinked[TechnologyDescriptionImpl])
    gameRules.terrainTypes ++= config.getConfigList("terrainTypes").asScala.map(_.as[TerrainTypeImpl])
    gameRules.resources ++= config.getConfigList("resources").asScala.map(_.asLinked[ResourceTypeImpl])
    gameRules.cityBuildings ++= config.getConfigList("cityBuildings").asScala.map(_.asLinked[CityBuildingTypeImpl])
    gameRules.langUpgradeTypes ++= config.getConfigList("landUpgrades").asScala.map(_.asLinked[LandUpgradeTypeImpl])
    gameRules.unitTypes ++= config.getConfigList("units").asScala.map(_.asLinked[GameUnitTypeImpl])
    gameRules.nations ++= config.getConfigList("nations").asScala.map(_.asLinked[NationImpl])

    linking.execute()

    gameRules
  }
}

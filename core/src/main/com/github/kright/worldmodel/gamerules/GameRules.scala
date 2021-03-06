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
import com.typesafe.config.{Config, ConfigObject, ConfigValue, ConfigValueFactory}

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
  def technologies: GameRulesHolder[TechnologyDescriptionView]

  def bioms: GameRulesHolder[Biom]

  // uses bioms
  def terrainModifiers: GameRulesHolder[TerrainModifier]

  // uses techs and terrain types
  def resources: GameRulesHolder[ResourceType]

  // uses someself, resources and technologies
  def cityBuildings: GameRulesHolder[CityBuildingType]

  // uses terrain types
  def langUpgradeTypes: GameRulesHolder[LandUpgradeType]

  // uses tech, resources, cityBuildingTypes, and itself
  def unitTypes: GameRulesHolder[GameUnitTypeView]

  // uses techs, unitTypes, cityBuildings
  def nations: GameRulesHolder[NationView]
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
  var technologies: GameRulesHolder[TechnologyDescriptionView] = new GameRulesHolder()
  var bioms: GameRulesHolder[Biom] = new GameRulesHolder()
  var terrainModifiers: GameRulesHolder[TerrainModifier] = new GameRulesHolder()
  var resources: GameRulesHolder[ResourceType] = new GameRulesHolder()
  var cityBuildings: GameRulesHolder[CityBuildingType] = new GameRulesHolder()
  var langUpgradeTypes: GameRulesHolder[LandUpgradeType] = new GameRulesHolder()
  var unitTypes: GameRulesHolder[GameUnitTypeView] = new GameRulesHolder()
  var nations: GameRulesHolder[NationView] = new GameRulesHolder()
}


object GameRules extends ConfigConverter[GameRulesImpl] {

  import ConfigLoader._

  override def convert(config: Config): GameRulesImpl = {

    implicit val gameRules: GameRulesImpl = new GameRulesImpl()
    implicit val linking: DilatedExecutor = new DilatedExecutor()

    gameRules.technologies ++= config.getNamedEntries("technologies").map(_.asLinked[TechnologyDescription])
    gameRules.bioms ++= config.getNamedEntries("bioms").map(_.as[Biom])
    gameRules.terrainModifiers ++= config.getNamedEntries("terrainModifiers").map(_.asLinked[TerrainModifier])
    gameRules.resources ++= config.getNamedEntries("resources").map(_.asLinked[ResourceType])
    gameRules.cityBuildings ++= config.getNamedEntries("cityBuildings").map(_.asLinked[CityBuildingType])
    gameRules.langUpgradeTypes ++= config.getNamedEntries("landUpgrades").map(_.asLinked[LandUpgradeType])
    gameRules.unitTypes ++= config.getNamedEntries("units").map(_.asLinked[GameUnitType])
    gameRules.nations ++= config.getNamedEntries("nations").map(_.asLinked[Nation])

    linking.execute()

    gameRules
  }
}

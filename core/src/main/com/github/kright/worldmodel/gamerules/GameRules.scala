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
  def technologies: Seq[TechnologyDescription]

  def terrainTypes: Seq[TerrainType]

  // uses techs and terrain types
  def resources: Seq[ResourceType]

  // uses someself, resources and technologies
  def cityBuildings: Seq[CityBuildingType]

  // uses terrain types
  def langUpgradeTypes: Seq[LandUpgradeType]

  // uses tech, resources, cityBuildingTypes, and itself
  def unitTypes: Seq[GameUnitType]

  // uses techs, unitTypes, cityBuildings
  def nations: Seq[Nation]
}

class GameRulesImpl(var technologies: Seq[TechnologyDescription],
                    var terrainTypes: Seq[TerrainType],
                    var resources: Seq[ResourceType],
                    var cityBuildings: Seq[CityBuildingType],
                    var langUpgradeTypes: Seq[LandUpgradeType],
                    var unitTypes: Seq[GameUnitType],
                    var nations: Seq[Nation]) extends GameRules

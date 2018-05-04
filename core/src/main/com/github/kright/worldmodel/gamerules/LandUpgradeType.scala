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
  * Created by Igor Slobodskov on 26 April 2018
  */
trait LandUpgradeType extends HasName {

  def possibleTerrain: mutable.Set[TerrainType]

  def resources: ResourcesRequirement
}

sealed trait ResourcesRequirement

case object AllowAll extends ResourcesRequirement

case class ResourcesInList(permittedResources: Set[ResourceType]) extends ResourcesRequirement

class LandUpgradeTypeImpl(var name: String,
                          var resources: ResourcesRequirement,
                          val possibleTerrain: mutable.Set[TerrainType]) extends LandUpgradeType

object LandUpgradeType extends DilatedConverter[LandUpgradeTypeImpl] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): LandUpgradeTypeImpl = {
    new LandUpgradeTypeImpl(config.getString("name"), null, new mutable.HashSet[TerrainType]) {
      this.doLate {
        resources = if (config.hasPath("resources")) {
          ResourcesInList(config.getStrings("resources").map(gameRules.resources(_)).toSet)
        } else {
          AllowAll
        }

        possibleTerrain ++= config.getStrings("terrain").map(gameRules.terrainTypes(_))
      }
    }
  }
}
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
sealed trait ResourcesRequirement

case object AllowAll extends ResourcesRequirement

case class ResourcesInList(permittedResources: Set[ResourceType]) extends ResourcesRequirement

class LandUpgradeType(val name: String,
                      val productionBonus: CellProduction,
                      val bioms: mutable.Set[Biom],
                      val onlyOnHeight: Option[Int],
                      val defenceBonus: Int,
                      var resources: ResourcesRequirement) extends HasName

object LandUpgradeType extends DilatedConverter[LandUpgradeType] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): LandUpgradeType = {
    new LandUpgradeType(
      name = config.getString("name"),
      productionBonus = config.getOption[Config]("production").map(_.as[CellProduction]).getOrElse(new CellProduction()),
      bioms = new mutable.HashSet(),
      onlyOnHeight = config.getOption[Int]("onlyHeight"),
      defenceBonus = config.getOption[Int]("defenceBonus").getOrElse(0),
      resources = null) { // late
      this.doLate {
        resources = if (config.hasPath("resources")) {
          ResourcesInList(config.getStrings("resources").map(gameRules.resources(_)).toSet)
        } else {
          AllowAll
        }

        bioms ++= config.getStrings("terrain").map(gameRules.bioms(_))
      }
    }
  }
}
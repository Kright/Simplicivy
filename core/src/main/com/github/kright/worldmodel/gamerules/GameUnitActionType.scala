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
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 28 April 2018
  */
sealed trait GameUnitActionType

sealed trait WorkerAction extends GameUnitActionType


case object BuildCity extends GameUnitActionType

case object Destroy extends GameUnitActionType

case class Terraforming(var to: TerrainType,
                        moves: Int,
                        requirement: CellActionRequirement) extends WorkerAction

case class BuildingLandUpgrade(var upgrade: LandUpgradeType,
                               moves: Int,
                               requirement: CellActionRequirement) extends GameUnitActionType with WorkerAction

case class BuildRoad(moves: Int,
                     requirement: CellActionRequirement) extends WorkerAction


class CellActionRequirement(var terrain: mutable.Set[TerrainType] = new mutable.HashSet[TerrainType](),
                            var technology: ArrayBuffer[TechnologyDescription] = new ArrayBuffer[TechnologyDescription]())

object GameUnitActionType extends DilatedConverter[GameUnitActionType] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): GameUnitActionType = {
    config.getString("name") match {
      case "buildCity" => BuildCity
      case "destroy" => Destroy
      case "terraforming" => new Terraforming(
        to = null, //late init
        moves = config.getInt("moves"),
        requirement = cellActionRequirement(config.getConfig("requirement"))
      ) {
        this.doLate {
          to = gameRules.terrainTypes(config.getString("terrain"))
        }
      }
      case "landUpgrade" => new BuildingLandUpgrade(
        upgrade = null, //late init
        moves = config.getInt("moves"),
        requirement = cellActionRequirement(config.getConfig("requirement"))) {
        this.doLate {
          upgrade = gameRules.langUpgradeTypes(config.getString("upgrade"))
        }
      }
      case "buildRoad" => BuildRoad(
        moves = config.getInt("moves"),
        requirement = cellActionRequirement(config.getConfig("requirement"))
      )
      case actionName: String => throw new ParsingError(
        s"unknown action name: $actionName at line ${config.origin().lineNumber()} in $config")
    }
  }

  private def cellActionRequirement(cfg: Config)(implicit gameRules: GameRules, dilatedExecutor: DilatedExecutor): CellActionRequirement =
    new CellActionRequirement() {
      this.doLate {
        terrain ++= cfg.getStrings("terrain").map(gameRules.terrainTypes(_))
        technology ++= cfg.getStrings("technology").map(gameRules.technologies(_))
      }
    }
}

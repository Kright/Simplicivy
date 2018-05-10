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

case class BuildingLandUpgrade(var upgrade: LandUpgradeType,
                               moves: Int,
                               requirement: CellActionRequirement) extends GameUnitActionType with WorkerAction

case class BuildRoad(moves: Int,
                     requirement: CellActionRequirement) extends WorkerAction


class CellActionRequirement(var bioms: mutable.Set[Biom] = new mutable.HashSet(),
                            var technology: ArrayBuffer[TechnologyDescriptionView] = new ArrayBuffer[TechnologyDescriptionView]())

object GameUnitActionType extends DilatedConverter[Seq[GameUnitActionType]] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): Seq[GameUnitActionType] = {
    val all = new ArrayBuffer[GameUnitActionType]()

    if (config.hasPath("buildCity"))
      all += BuildCity

    if (config.hasPath("destroy"))
      all += Destroy

    all ++= config.getConfigs("buildRoad").map { c =>
      BuildRoad(c.getInt("moves"), cellActionRequirement(c.getConfig("require")))
    }

    all ++= config.getConfigs("upgradeLand").map { c =>
      new BuildingLandUpgrade(
        upgrade = null,
        moves = c.getInt("moves"),
        requirement = cellActionRequirement(c.getConfig("require"))
      ) {
        this.doLate {
          upgrade = gameRules.langUpgradeTypes(c.getString("upgrade"))
        }
      }
    }

    all
  }


  private def cellActionRequirement(cfg: Config)(implicit gameRules: GameRules, dilatedExecutor: DilatedExecutor): CellActionRequirement =
    new CellActionRequirement() {
      this.doLate {
        bioms ++= cfg.getStrings("bioms").map(gameRules.bioms(_))
        technology ++= cfg.getStrings("technology").map(gameRules.technologies(_))
      }
    }
}

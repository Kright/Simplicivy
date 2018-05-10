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

class TerrainModifier(val name: String,
                      val produces: CellProduction,
                      val additionalMovementCost: Int = 0,
                      val additionalDefenseBonus: Int = 0,
                      val requirements: TerrainModifierRequirements) extends HasName {
}
class TerrainModifierRequirements {
  val bioms: mutable.Set[Biom] = new mutable.HashSet()
  var onlyHeight: Option[Int] = None
  var onLand: Boolean = true
  var onWater: Boolean = false
  var requireRiver: Boolean = false
}


object TerrainModifier extends DilatedConverter[TerrainModifier] {

  import ConfigLoader._

  private def toRequirements(c: Config)(implicit gameRules: GameRules, dilatedExecutor: DilatedExecutor): TerrainModifierRequirements = {
    new TerrainModifierRequirements {
      this.doLate {
        bioms ++= c.getStrings("bioms").map(gameRules.bioms(_))
        onlyHeight = c.getOption[Int]("only height")
        onLand = c.getOption[Boolean]("onLand").getOrElse(true)
        onWater = c.getOption[Boolean]("onWater").getOrElse(false)
        requireRiver = c.getOption[Boolean]("river").getOrElse(false)
      }
    }
  }

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): TerrainModifier =
    new TerrainModifier(
      name = config.getString("name"),
      produces = config.getOption[Config]("produces").map(_.as[CellProduction]).getOrElse(new CellProduction()),
      additionalMovementCost = config.getOption[Int]("addMoveCost").getOrElse(0),
      additionalDefenseBonus = config.getOption[Int]("addDefenceBonus").getOrElse(0),
      requirements = toRequirements(config.getConfig("require"))
    )
}

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
// sea and lake has special biomes
class Biom(val name: String,
           val produces: CellProduction,
           val movementCost: Int,
           val defenceBonus: Int,
           val isLand: Boolean = true) extends HasName {
  def isWater = !isLand
}


object Biom extends ConfigConverter[Biom] {

  import ConfigLoader._

  override def convert(config: Config): Biom = {

    new Biom(
      name = config.getString("name"),
      produces = config.getConfig("produces").as[CellProduction],
      movementCost = config.getOption[Int]("movementCost").getOrElse(1),
      defenceBonus = config.getOption[Int]("defenceBonus").getOrElse(0),
      isLand = config.getOption[Boolean]("land").getOrElse(config.getOption[Boolean]("water").getOrElse(false))
    )
  }
}

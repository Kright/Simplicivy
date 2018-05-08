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

import com.typesafe.config.Config

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait TerrainTypeView extends HasName {

  def isLand: Boolean

  def isWater: Boolean = !isLand

  /**
    * will be used for visibility calculations
    * height <= 0 for water, 1 for land, 2 for hills, 3 for mountains
    * for water height is a depth
    * When you stay on mountain above plain land, you can see farther
    */
  def height: Int

  def movementCost: Int

  def defenceBonus: Int

  def produces: CellProductionView
}

class TerrainType(var name: String,
                  var isLand: Boolean,
                  var height: Int,
                  var movementCost: Int,
                  var defenceBonus: Int,
                  var produces: CellProduction) extends TerrainTypeView {
  def this() {
    this(null, true, 0, 0, 0, null)
  }
}

object TerrainType extends ConfigConverter[TerrainType] {

  import ConfigLoader._

  override def convert(config: Config): TerrainType = {

    new TerrainType(config.getString("name"),
      config.getBoolean("isLand"),
      config.getInt("height"),
      config.getOption[Int]("movementCost").getOrElse(1),
      config.getOption[Int]("defenceBonus").getOrElse(0),
      config.getConfig("produces").as[CellProduction]
    )
  }
}

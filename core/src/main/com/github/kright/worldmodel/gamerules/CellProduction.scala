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
  * Created by Igor Slobodskov on 28 April 2018
  */
trait CellProduction {

  def food: Int

  def production: Int

  def commerce: Int
}

class MutableCellProduction(var food: Int,
                            var production: Int,
                            var commerce: Int) extends CellProduction

object CellProduction extends ConfigConverter[MutableCellProduction] {
  override def convert(config: Config): MutableCellProduction = {
    import ConfigLoader._
    implicit def str2int(s: String) = config.getOption[Int](s).getOrElse(0)

    new MutableCellProduction(
      food = "food",
      production = "production",
      commerce = "commerce")
  }
}
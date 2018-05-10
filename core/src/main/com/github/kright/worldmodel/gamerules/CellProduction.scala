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
class CellProduction(var food: Int = 0,
                     var production: Int = 0,
                     var commerce: Int = 0,
                     var foodMultiplier: Int = 1,
                     var productionMultiplier: Int = 1,
                     var commerceMultiplier: Int = 1)

object CellProduction extends ConfigConverter[CellProduction] {
  override def convert(config: Config): CellProduction = {
    import ConfigLoader._
    implicit def str2int(pair: (String, Int)) = config.getOption[Int](pair._1).getOrElse(pair._2)

    new CellProduction(
      food = "food" -> 0,
      production = "production" -> 0,
      commerce = "commerce" -> 0,
      foodMultiplier = "foodMultiplier" -> 1,
      productionMultiplier = "productionMultiplier" -> 1,
      commerceMultiplier = "commerceMultiplier" -> 1)
  }
}

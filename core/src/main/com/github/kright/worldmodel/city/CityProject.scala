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

package com.github.kright.worldmodel.city

import com.github.kright.worldmodel.gamerules.{CityBuildingType, GameUnitType}

/**
  * Created by Igor Slobodskov on 22 May 2018
  */
sealed trait CityProject {
  def progress: Int

  def totalCost: Int
}

object CityProject {

  case class ProduceUnit(progress: Int, totalCost: Int, unit: GameUnitType) extends CityProject

  case class AddBuilding(progress: Int, totalCost: Int, building: CityBuildingType) extends CityProject

  case object Empty extends CityProject {
    override def progress: Int = 0

    override def totalCost: Int = 0
  }

}

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

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
sealed trait RoadType {
  def isRoad: Boolean
}

case object NoRoad extends RoadType {
  override def isRoad: Boolean = false
}

/**
  * @param movesMultiplier how much faster units move on roads
  */
case class Road(movesMultiplier: Int) extends RoadType {
  override def isRoad: Boolean = true
}

/**
  * @param maxDistance max distance for moving on railroad
  *                    distance doesn't depends on moves count of unit
  */
case class Railroad(maxDistance: Int) extends RoadType {
  override def isRoad: Boolean = true
}

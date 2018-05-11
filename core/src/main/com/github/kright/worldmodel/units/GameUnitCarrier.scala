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

package com.github.kright.worldmodel.units

import scala.collection.mutable.ArrayBuffer

/**
  * units (like ships, which can carry land units over sea)
  *
  * Created by Igor Slobodskov on 11 May 2018
  */
trait GameUnitCarrierView {
  def carried: Seq[GameUnitView]

  def maxCarried: Int

  def canBoardUnit(unit: GameUnitView): Boolean
}


class GameUnitCarrier(self: GameUnitView) extends GameUnitCarrierView {
  val carried = new ArrayBuffer[GameUnitView]

  override def maxCarried: Int = carried.size

  /** can carry land unit of same country if not full*/
  override def canBoardUnit(unit: GameUnitView): Boolean =
    unit.owner == self.owner && carried.size < maxCarried && unit.unitType.landMoves.isDefined
}

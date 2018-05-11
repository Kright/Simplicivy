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

import com.github.kright.worldmodel.gamerules.GameUnitLevel

/**
  * Created by Igor Slobodskov on 11 May 2018
  */
trait GameUnitMilitaryView {
  def exp: Int

  def level: GameUnitLevel

  def hp: Int

  def maxHp: Int
}


class GameUnitMilitary(unit: GameUnit, startExp: Int) extends GameUnitMilitaryView {
  private val unitType = unit.unitType

  var exp: Int = startExp
  var level: GameUnitLevel = findLevel()

  def maxHp = level.maxHitPoints

  var hp: Int = maxHp
  var meleeAttacks: Int = unitType.meleeCombat.map(_.attacksPerMove).getOrElse(0)
  var rangeAttacks: Int = unitType.rangeAttack.map(_.attacksPerMove).getOrElse(0)

  private def findLevel(): GameUnitLevel =
    unitType.levels.view.filter(exp >= _.requiredExperience).maxBy(_.requiredExperience)
}

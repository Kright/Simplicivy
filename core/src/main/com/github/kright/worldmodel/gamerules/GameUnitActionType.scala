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
  * Created by Igor Slobodskov on 28 April 2018
  */
sealed trait GameUnitActionType

sealed trait WorkerAction extends GameUnitActionType


case object BuildCity extends GameUnitActionType

case object Destroy extends GameUnitActionType

case class Terraforming(to: TerrainType,
                        cost: Int,
                        requirement: CellActionRequirement) extends WorkerAction

case class BuildingLandUpgrade(upgrade: LandUpgradeType,
                               cost: Int,
                               requirement: CellActionRequirement)
  extends GameUnitActionType with WorkerAction

case class BuildRoad(cost: Int,
                     requirement: CellActionRequirement) extends WorkerAction


class CellActionRequirement(val terrain: Set[TerrainType],
                            val technology: Seq[TechnologyDescription] = Seq())

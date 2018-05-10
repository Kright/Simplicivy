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

import com.github.kright.worldmodel.MapCell
import com.github.kright.worldmodel.gamerules.{RoadType, TerrainModifier}
import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * awaiting orders - when don't know what to do
  * idle - when player say "do nothing"
  */
sealed trait GameUnitActivity

object GameUnitActivity {

  case object AwaitingOrders extends GameUnitActivity

  case object Idle extends GameUnitActivity

  case object Defence extends GameUnitActivity

  case object BuildCity extends GameUnitActivity

  case class MoveTo(p: MapPosition, unit: GameUnitView) extends GameUnitActivity

  case class BuildSomething(build: WorkerTask) extends GameUnitActivity

  case class Attack(task: AttackType, attacker: GameUnitView, attacked: MapCell) extends GameUnitActivity

}


sealed trait WorkerTask

object WorkerTask {

  // todo fix case when more than one worker do same task

  case class BuildRoad(road: RoadType, remainingProgress: Int) extends WorkerTask

  case class BuildLandImprovement(improvement: RoadType, remainingProgress: Int) extends WorkerTask

  case class ChangeTerrainModifier(newModifier: TerrainModifier) extends WorkerTask

}

sealed trait AttackType

object AttackType {

  case object MeleeAttack extends AttackType

  case object RangeAttack extends AttackType

  case object AirBombardment extends AttackType

}
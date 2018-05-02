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

import com.github.kright.worldmodel.gamerules.{RoadType, TerrainType}
import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * awaiting orders - when don't know what to do
  * idle - when player say "do nothing"
  */
sealed trait GameUnitActivity

case object AwaitingOrders extends GameUnitActivity

case object Idle extends GameUnitActivity

case object Defence extends GameUnitActivity

case class MoveTo(p: MapPosition) extends GameUnitActivity

case class BuildSomething(build: WorkerTask) extends GameUnitActivity


// todo fix case when more than one worker do same task
sealed trait WorkerTask

case class BuildRoad(road: RoadType, remainingProgress: Int) extends WorkerTask

case class BuildLandImprovement(improvement: RoadType, remainingProgress: Int) extends WorkerTask

case class ChangeTerrainType(newTerrain: TerrainType) extends WorkerTask

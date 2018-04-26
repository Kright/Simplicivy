package com.github.kright.worldmodel.units

import com.github.kright.worldmodel.{RoadType, TerrainType}
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

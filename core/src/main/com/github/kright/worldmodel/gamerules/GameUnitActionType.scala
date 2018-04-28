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

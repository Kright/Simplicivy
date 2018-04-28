package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait Resource extends HasId {

  def resourceType: ResourceType

  def requiredTerrain: Set[TerrainType]

  def requiredTechnology: Option[TechnologyDescription]

  def cellBonus: CellProduction
}

sealed trait ResourceType

case object BonusResource extends ResourceType

case object StrategicResource extends ResourceType

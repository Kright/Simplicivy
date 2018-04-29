package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait ResourceType extends HasId {

  def resourceType: ResourceKind

  def requiredTerrain: Set[TerrainType]

  def requiredTechnology: Option[TechnologyDescription]

  def cellBonus: CellProduction
}

sealed trait ResourceKind

case object BonusResource extends ResourceKind

case object StrategicResource extends ResourceKind

case object LuxuryResource extends ResourceKind

package com.github.kright.worldmodel

import com.github.kright.worldmodel.units.GameUnit
import com.github.kright.worldmodel.worldmap.MapPosition
import com.github.kright.worldmodel.city.City
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.{LandUpgradeType, ResourceType, RoadType, TerrainType}


/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait MapCell extends MapPosition with LandModifiers {

  def visibility: CellVisibility

  def units: Seq[GameUnit]

  def owner: Option[CountryLink]

  /**
    * @return if shadowed return self else make shadowed copy
    */
  def getShadowed(): MapCell
}


trait LandModifiers {
  def terrain: TerrainType

  def resource: Option[ResourceType]

  def road: RoadType

  def landUpgrade: Option[LandUpgradeType]

  def hasPollution: Boolean

  def city: Option[City]
}

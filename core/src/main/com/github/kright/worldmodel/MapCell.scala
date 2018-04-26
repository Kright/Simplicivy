package com.github.kright.worldmodel

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait MapCell extends MapPosition with LandModifiers {

  def visibility: CellVisibility

  def units: Seq[Unit]

  def owner: Option[Country]

  /**
    * @return if shadowed return self else make shadowed copy
    */
  def getShadowed(): MapCell
}


trait LandModifiers {
  def terrain: TerrainType

  def resource: Option[Resource]

  def road: RoadType

  def landUpgrade: Option[LandUpgrade]

  def hasPollution: Boolean

  def settlement: Option[Settlement]
}

package com.github.kright.worldmodel

import com.github.kright.worldmodel.units.GameUnit
import com.github.kright.worldmodel.city.City

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
class MutableMapCell(val x: Int, val y: Int) extends MapCell {
  override def visibility: CellVisibility = Visible

  var terrain: TerrainType = _

  var city: Option[City] = None

  var resource: Option[Resource] = None

  val units: ArrayBuffer[GameUnit] = new ArrayBuffer[GameUnit]()

  var owner: Option[Country] = None

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = new ShadowedMapCell(this)

  var road: RoadType = NoRoad

  var landUpgrade: Option[LandUpgrade] = None

  var hasPollution: Boolean = false
}


class ShadowedMapCell private(val x: Int, val y: Int,
                              val terrain: TerrainType,
                              val city: Option[City],
                              val resource: Option[Resource],
                              val owner: Option[Country],
                              val road: RoadType,
                              val landUpgrade: Option[LandUpgrade],
                              val hasPollution: Boolean) extends MapCell {

  def this(c: MapCell) = this(c.x, c.y, c.terrain, c.city, c.resource, c.owner, c.road,
    c.landUpgrade, c.hasPollution)

  override def visibility: CellVisibility = Shadowed

  override def units: Seq[GameUnit] = List.empty

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = this
}


class UnknownMapCell(val x: Int, val y: Int) extends MapCell {
  override def visibility: CellVisibility = Unknown


  private def throwException = throw new RuntimeException("this is Unknown cell!")


  override def terrain: TerrainType = throwException

  override def city: Option[City] = throwException

  override def resource: Option[Resource] = throwException

  override def units: Seq[GameUnit] = throwException

  override def owner: Option[Country] = throwException

  /**
    * @return if shadowed return self else make shadowed copy
    */
  override def getShadowed(): MapCell = throwException

  override def road: RoadType = throwException

  override def landUpgrade: Option[LandUpgrade] = throwException

  override def hasPollution: Boolean = throwException
}

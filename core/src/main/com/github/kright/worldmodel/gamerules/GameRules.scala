package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.ObjectOwner

/**
  * Created by Igor Slobodskov on 27 April 2018
  *
  * effectively persistent structure about game rules
  * technologies tree, city buildings, unit types.
  *
  */
trait GameRules {

  def resources: Seq[Resource]

  def technologies: ObjectOwner[TechnologyDescription]

  def cityBuildings: ObjectOwner[CityBuildingType]

  def unitTypes: ObjectOwner[GameUnitType]

  def terrainTypes: ObjectOwner[TerrainType]

  def roadTypes: Seq[RoadType] = Seq(NoRoad, Road, Railroad)
}

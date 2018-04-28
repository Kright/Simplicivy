package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.ObjectOwner

/**
  * Created by Igor Slobodskov on 27 April 2018
  *
  * effectively persistent structure about game rules
  * technologies tree, city buildings, unit types and what needed to play a game
  *
  * Some information may be redundant:
  * for example, Resource contents links to several terrain types (on which them may be placed)
  * and information has to be consistent.
  * resources collection must contain all possible values! And about others: too
  */
trait GameRules {

  def resources: Seq[Resource]

  def technologies: ObjectOwner[TechnologyDescription]

  def cityBuildings: ObjectOwner[CityBuildingType]

  def unitTypes: ObjectOwner[GameUnitType]

  def terrainTypes: ObjectOwner[TerrainType]

  def langUpgradeTypes: ObjectOwner[LandUpgradeType]
}

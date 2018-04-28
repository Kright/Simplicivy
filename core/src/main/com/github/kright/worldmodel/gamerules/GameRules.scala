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

  // pure classes, don't hold links to other structures

  def technologies: ObjectOwner[TechnologyDescription]

  def terrainTypes: ObjectOwner[TerrainType]

  // uses techs and terrain types
  def resources: Seq[ResourceType]

  // uses someself, resources and technologies
  def cityBuildings: ObjectOwner[CityBuildingType]

  // uses terrain types
  def langUpgradeTypes: ObjectOwner[LandUpgradeType]

  // uses tech, resources, cityBuildingTypes, and itself
  def unitTypes: ObjectOwner[GameUnitType]

  // uses techs, unitTypes, cityBuildings
  def nations: Seq[Nation]
}

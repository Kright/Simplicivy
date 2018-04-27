package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.{ObjectOwner, SimpleObjectOwner}
import com.github.kright.worldmodel.city.CityBuilding
import com.github.kright.worldmodel.science.TechnologyDescription
import com.github.kright.worldmodel.units.GameUnitType

/**
  * Created by Igor Slobodskov on 27 April 2018
  *
  * effectively persistent structure about game rules
  * technologies tree, city buildings, unit types.
  *
  */
trait GameRules {

  def allTechnologies: ObjectOwner[TechnologyDescription]

  def cityBuildings: ObjectOwner[CityBuilding]

  def unitTypes: ObjectOwner[GameUnitType]
}

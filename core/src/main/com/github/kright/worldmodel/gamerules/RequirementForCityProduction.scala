package com.github.kright.worldmodel.gamerules

/**
  * Created by Igor Slobodskov on 28 April 2018
  */

class RequirementForCityProduction(val cost: Int,
                                   val technology: Option[TechnologyDescription],
                                   val resources: Seq[ResourceType],
                                   val cityBuildings: Seq[CityBuildingType],
                                   val requireRiver: Boolean = false,
                                   val requireSea: Boolean = false)

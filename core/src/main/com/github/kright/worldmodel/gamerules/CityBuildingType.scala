package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait CityBuildingType extends HasId {

  def requires: RequirementForCityProduction

  def cost: Int = requires.cost

  def maintenance: Int

  //todo positive effects
}

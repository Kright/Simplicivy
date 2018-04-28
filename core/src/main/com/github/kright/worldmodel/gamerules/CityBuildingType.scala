package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait CityBuildingType extends HasId{
  def cost: Int

  def maintenance: Int

  def requires: Requirements
}

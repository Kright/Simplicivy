package com.github.kright.worldmodel

import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait Unit extends MapPosition {

  def name: String

  def unitType: UnitType
}

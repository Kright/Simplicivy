package com.github.kright.worldmodel.city

import com.github.kright.worldmodel.Country
import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait City extends MapPosition {

  def name: String

  def citizensCount: Int

  def owner: Country

  def buildings: Seq[CityBuilding]
}

package com.github.kright.worldmodel.city

import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.CellProduction
import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait City extends MapPosition {

  def name: String

  def citizensCount: Int

  def owner: CountryLink

  def buildings: Seq[CityBuilding]

  def production: CellProduction
}

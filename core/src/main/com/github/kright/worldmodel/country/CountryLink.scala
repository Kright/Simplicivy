package com.github.kright.worldmodel.country

import com.github.kright.utils.HasId
import com.github.kright.worldmodel.city.City
import com.github.kright.worldmodel.gamerules.GameRules
import com.github.kright.worldmodel.science.PlayerTechnologies
import com.github.kright.worldmodel.units.GameUnit

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * interface for country view
  */
trait CountryLink {
  // may there would be view of current player for this country or something else
}


trait Country extends HasId {

  //different counties may have different units types, buildings and etc.
  def gameRules: GameRules

  def cities: Seq[City]

  def units: Seq[GameUnit]

  def gold: Int

  def culture: Int

  def science: PlayerTechnologies
}

package com.github.kright.worldmodel.units

import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.GameUnitType
import com.github.kright.worldmodel.worldmap.MapPosition

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  */
trait GameUnit extends MapPosition {

  //may be edited py player
  def name: String


  def movementPoints: Int

  def attackPoints: Int // some units may attack twice


  def activity: GameUnitActivity

  def hp: Int

  def maxHp: Int

  def unitType: GameUnitType

  def owner: CountryLink
}

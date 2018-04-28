package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait TerrainType extends HasId {

  def land: Boolean

  def movementCost: Int

  def defenceBonus: Int

  def produces: CellProduction
}

package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait TerrainType extends HasId {

  def isLand: Boolean

  @inline
  def isWater: Boolean = !isLand

  /**
    * will be used for visibility calculations
    * 0 for water, 1 for land, 2 for hills, 3 for mountains
    * When you stay on mountain above plain land, you can see farther
    */
  def height: Int

  def movementCost: Int

  def defenceBonus: Int

  def produces: CellProduction
}

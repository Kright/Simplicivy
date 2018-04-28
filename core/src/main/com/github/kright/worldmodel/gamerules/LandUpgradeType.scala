package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait LandUpgradeType extends HasId {

  def possibleTerrain: Set[TerrainType]
}

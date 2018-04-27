package com.github.kright.worldmodel.science

import com.github.kright.utils.HasId
import com.github.kright.worldmodel.LandUpgrade
import com.github.kright.worldmodel.city.CityBuilding
import com.github.kright.worldmodel.units.GameUnitType

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait TechnologyDescription extends HasId {
  def scienceCost: Int

  def requiredTechnologies: Seq[TechnologyDescription]

  def units: Seq[GameUnitType]

  def buildings: Seq[CityBuilding]

  def landUpgrades: Seq[LandUpgrade]


  @inline
  def isResearched(implicit playerTech: PlayerTechnologies): Boolean = playerTech.isResearched(this)

  @inline
  def isAvailable(implicit playerTech: PlayerTechnologies): Boolean =
    !isResearched && requiredTechnologies.forall(_.isResearched)
}

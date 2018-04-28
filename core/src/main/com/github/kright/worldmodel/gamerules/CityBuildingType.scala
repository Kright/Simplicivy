package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait CityBuildingType extends HasId {

  def requires: RequirementForCityProduction

  def cost: Int = requires.cost


  def buildingEffect: BuildingEffect
}

trait BuildingEffect {

  def maintenance: Int

  def happines: Int

  def culture: Int


  def taxBonus: Int

  def researchBonus: Int

  def productionBonus: Int

  def defenceBonus: Int

  def corruptionDecrease: Int


  def pollution: Int
}

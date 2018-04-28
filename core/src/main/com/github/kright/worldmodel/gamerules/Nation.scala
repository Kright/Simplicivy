package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 29 April 2018
  */
trait Nation extends HasId {

  def name: String

  def features: NationFeatures


  def startingTechs: Seq[TechnologyDescription]

  def extraGameUnits: Seq[GameUnitType]

  def extraBuildings: Seq[CityBuildingType]
}

trait NationFeatures {
  def moreFood: Boolean

  def moreCommerce: Boolean

  def moreProduction: Boolean


  def landExpansion: Boolean

  def seaExpansion: Boolean


  def military: Boolean

  def scientific: Boolean
}

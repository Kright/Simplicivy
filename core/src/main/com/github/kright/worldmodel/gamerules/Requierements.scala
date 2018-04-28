package com.github.kright.worldmodel.gamerules

import javax.annotation.Resources

/**
  * Created by Igor Slobodskov on 28 April 2018
  */
trait Requirements {
  def technology: TechnologyDescription

  def resources: Seq[Resources]

  def buildings: Seq[CityBuildingType]

  //todo nation requirements
}

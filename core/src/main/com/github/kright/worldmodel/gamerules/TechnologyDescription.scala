package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.HasId
import com.github.kright.worldmodel.science.PlayerTechnologies

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait TechnologyDescription extends HasId {
  def scienceCost: Int

  def requiredTechnologies: Seq[TechnologyDescription]


  @inline
  def isResearched(implicit playerTech: PlayerTechnologies): Boolean = playerTech.isResearched(this)

  @inline
  def isAvailable(implicit playerTech: PlayerTechnologies): Boolean =
    !isResearched && requiredTechnologies.forall(_.isResearched)

  def leadTo(implicit gameRules: GameRules) =
    gameRules.technologies.all.filter(_.requiredTechnologies.contains(this))
}

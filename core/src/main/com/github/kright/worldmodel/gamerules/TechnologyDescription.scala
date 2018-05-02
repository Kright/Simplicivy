/*
 *     Copyright 2018 Igor Slobodskov
 *
 *     This file is part of Simplicivy.
 *
 *     Simplicivy is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Simplicivy is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Simplicivy.  If not, see <http://www.gnu.org/licenses/>.
 */

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

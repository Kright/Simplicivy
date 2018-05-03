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

package com.github.kright.worldmodel.science

import com.github.kright.worldmodel.gamerules.{GameRules, TechnologyDescription}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
private class PlayerTechnologiesImpl(implicit gameRules: GameRules) extends PlayerTechnologies {
  @inline
  implicit def playerTechnologies: PlayerTechnologies = this

  private val researchedList = new ArrayBuffer[TechnologyDescription]()
  private val researchedSet = new mutable.HashSet[TechnologyDescription]()

  private var availableList = computeAvailable()

  private def computeAvailable() = all.view.filter(_.isAvailable).toBuffer

  def research(newTech: TechnologyDescription): Unit = {
    assert(!newTech.isResearched)
    assert(newTech.requiredTechnologies.forall(isResearched))
    researchedSet += newTech
    researchedList += newTech
    availableList = computeAvailable()
  }

  override def available: Seq[TechnologyDescription] = availableList

  override def researched: Seq[TechnologyDescription] = researchedList

  override def isResearched(tech: TechnologyDescription): Boolean = researchedSet.contains(tech)

  override def all: Seq[TechnologyDescription] = gameRules.technologies
}

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

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
class PlayerScienceImpl(implicit gameRules: GameRules) extends PlayerScience {

  private val tech = new PlayerTechnologiesImpl()
  private val savedProgress = new mutable.HashMap[TechnologyDescription, Progress]()

  private var researchNow: Progress = null
  private var freeSciencePoints: Int = 0

  private def doResearch(): Boolean = {
    if (freeSciencePoints == 0)
      return false

    val newPoints = researchNow.progress + freeSciencePoints

    if (newPoints >= researchNow.tech.scienceCost) {
      freeSciencePoints = newPoints - researchNow.tech.scienceCost
      savedProgress.remove(researchNow.tech)
      researchNow = null
      true
    } else {
      freeSciencePoints = 0
      researchNow.progress = newPoints
      false
    }
  }

  override def addSciencePoints(points: Int): Boolean = {
    freeSciencePoints += points
    if (researchNow == null) {
      true
    } else {
      doResearch()
    }
  }

  override def setResearchedTechnology(tech: TechnologyDescription): Boolean = {
    researchNow = savedProgress.getOrElseUpdate(tech, new Progress(tech))
    doResearch()
  }


  override def currentResearch: Option[TechnologyDescription] = Option(researchNow).map(_.tech)

  override def currentProgress: Iterable[ResearchProgress] = savedProgress.values

  override def all: Seq[TechnologyDescription] = tech.all

  override def available: Seq[TechnologyDescription] = tech.available

  override def researched: Seq[TechnologyDescription] = tech.researched

  override def isResearched(t: TechnologyDescription): Boolean = t.isResearched(tech)
}

private class Progress(val tech: TechnologyDescription) extends ResearchProgress {
  var progress: Int = 0
}

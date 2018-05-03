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

import com.github.kright.worldmodel.gamerules.TechnologyDescription

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait PlayerScience extends PlayerTechnologies {

  /**
    * @param points added science points
    * @return true if require to start new technology research
    */
  def addSciencePoints(points: Int): Boolean

  /**
    * @param tech goal technology to research. Must be available
    * @return true if require to start new technology research
    */
  def setResearchedTechnology(tech: TechnologyDescription): Boolean


  def currentResearch: Option[TechnologyDescription]

  def currentProgress: Iterable[ResearchProgress]
}


trait PlayerTechnologies {

  def all: Seq[TechnologyDescription]

  def available: Seq[TechnologyDescription]

  def researched: Seq[TechnologyDescription]

  def isResearched(tech: TechnologyDescription): Boolean
}


trait ResearchProgress {
  def tech: TechnologyDescription

  def progress: Int
}

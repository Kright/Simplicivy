package com.github.kright.worldmodel.science

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait ResearchProgress {
  def tech: TechnologyDescription

  def currentResearchPoints: Int
}

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

  def all: Iterable[TechnologyDescription]

  def available: Seq[TechnologyDescription]

  def researched: Seq[TechnologyDescription]

  def isResearched(tech: TechnologyDescription): Boolean
}

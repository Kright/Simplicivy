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

  override def all: Iterable[TechnologyDescription] = tech.all

  override def available: Seq[TechnologyDescription] = tech.available

  override def researched: Seq[TechnologyDescription] = tech.researched

  override def isResearched(t: TechnologyDescription): Boolean = t.isResearched(tech)
}

private class Progress(val tech: TechnologyDescription) extends ResearchProgress {
  var progress: Int = 0
}

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

  override def all: Iterable[TechnologyDescription] = gameRules.technologies.all
}

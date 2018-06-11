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

package com.github.kright.worldmodel.city

import com.github.kright.worldmodel.MapCell
import com.github.kright.worldmodel.country.Country
import com.github.kright.worldmodel.utils.NewTurnListener
import com.github.kright.worldmodel.worldmap.MapPosition


/**
  * Created by Igor Slobodskov on 27 April 2018
  */
trait CityView extends MapPosition {

  def name: String

  def citizens: CityCitizens

  def buildings: CityBuildings

  def cells: CityCells

  def totalCulture: Int

  def owner: Country
}

class City(val center: MapCell,
           var name: String,
           var owner: Country) extends CityView with NewTurnListener {

  val citizens = new CityCitizens(this)
  val buildings = new CityBuildings(this)
  val cells = new CityCells(this)
  val resources = new CityResources()

  var totalCulture: Int = 0
  var totalFood: Int = 0

  var project: CityProject = CityProject.Empty(0)

  def calculateProduction(): CityProductionView = {
    ???
    //todo
  }


  override def newTurn(): Unit = {
    val prod = calculateProduction()

    addCulture(prod.culture)
    addFood(prod.food)
    addProduction(prod.production)
    owner.science.addSciencePoints(prod.science)
    owner.gold += prod.commerce

    project = project.addProgress(prod.production)
    if (project.progress > project.totalCost) {
      project match {
        case addB: CityProject.AddBuilding =>
      }
    }

    project match {
      case e: CityProject.Empty => project = CityProject.empty
      case _ =>
    }
  }

  override def turnFinished(): Unit = {
  }

  private def addCulture(culture: Int): Unit = {
    totalCulture += culture
    //todo territory growing
  }

  private def addFood(food: Int): Unit = {
    totalFood += food
    while (totalFood >= maxFood) {
      totalFood -= maxFood
      citizens.addCitizen()
    }

    if (food < 0) {
      //starvation
      totalFood = 0
      citizens.removeCitizen()
    }
  }

  private def addProduction(add: Int): Unit = {
    project = project.addProgress(add)
  }

  def maxFood: Int = if (buildings.effect.decreasedFoodToGrowth) 10 else 20

  override def x: Int = center.x

  override def y: Int = center.y
}

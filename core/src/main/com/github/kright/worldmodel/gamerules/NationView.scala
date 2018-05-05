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

import com.github.kright.utils.DilatedExecutor
import com.typesafe.config.Config

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 29 April 2018
  */
trait NationView extends HasName {

  def name: String

  def features: NationFeaturesView


  def startingTechs: Seq[TechnologyDescriptionView]

  //  def extraGameUnits: Modification[GameUnitType]
  //
  //  def extraBuildings: Modification[CityBuildingType]
  // todo in future
}


trait NationFeaturesView {
  def moreFood: Boolean

  def moreCommerce: Boolean

  def moreProduction: Boolean


  def landExpansion: Boolean

  def seaExpansion: Boolean


  def military: Boolean

  def scientific: Boolean

  def cultural: Boolean
}

class NationFeatures(var moreFood: Boolean,
                     var moreCommerce: Boolean,
                     var moreProduction: Boolean,
                     var landExpansion: Boolean,
                     var seaExpansion: Boolean,
                     var military: Boolean,
                     var scientific: Boolean,
                     var cultural: Boolean) extends NationFeaturesView


class Nation(val name: String,
             var features: NationFeatures,
             val startingTechs: ArrayBuffer[TechnologyDescriptionView] = new ArrayBuffer[TechnologyDescriptionView]()) extends NationView

object Nation extends DilatedConverter[Nation] {

  import ConfigLoader._

  override def convert(implicit cfg: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): Nation = {
    new Nation(
      name = cfg.getString("name"),
      features = nationFeatures(cfg)) {
      this.doLate {
        startingTechs ++= cfg.getStrings("startTechnologies").map(gameRules.technologies(_))
      }
    }
  }

  private def nationFeatures(c: Config) = {
    implicit def strToBoolean(s: String): Boolean = c.getOption[Boolean](s).getOrElse(false)

    new NationFeatures(
      moreFood = "moreFood",
      moreCommerce = "moreCommerce",
      moreProduction = "moreProduction",
      landExpansion = "landExpansion",
      seaExpansion = "seaExpansion",
      military = "military",
      scientific = "scientific",
      cultural = "cultural"
    )
  }
}

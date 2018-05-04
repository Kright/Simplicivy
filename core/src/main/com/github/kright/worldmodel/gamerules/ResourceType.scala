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

import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait ResourceType extends HasName {

  def resourceKind: ResourceKind

  def requiredTerrain: mutable.Set[TerrainType]

  def requiredTechnology: Option[TechnologyDescription]

  def cellBonus: CellProduction
}

sealed trait ResourceKind

case object BonusResource extends ResourceKind

case object StrategicResource extends ResourceKind

case object LuxuryResource extends ResourceKind

class ResourceTypeImpl(var name: String,
                       var resourceKind: ResourceKind,
                       var requiredTerrain: mutable.Set[TerrainType],
                       var requiredTechnology: Option[TechnologyDescription],
                       var cellBonus: MutableCellProduction) extends ResourceType

object ResourceType extends DilatedConverter[ResourceTypeImpl] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): ResourceTypeImpl = {
    new ResourceTypeImpl(config.getString("name"),
      config.getString("kind") match {
        case "bonus" => BonusResource
        case "strategic" => StrategicResource
        case "luxury" => LuxuryResource
      },
      new mutable.HashSet[TerrainType](),
      None,
      cellBonus = config.getConfig("bonus").as[MutableCellProduction]
    ) {
      this.doLate {
        requiredTerrain ++= config.getStrings("terrain").map(gameRules.terrainTypes(_))
        requiredTechnology = config.getOption[String]("requiredTechnology").map(gameRules.technologies(_))
      }
    }
  }

}
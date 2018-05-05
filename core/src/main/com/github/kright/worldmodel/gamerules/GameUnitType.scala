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
import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait GameUnitType extends HasName {

  // simple fields

  def movingOn: MovingEnvironment

  def isMilitary: Boolean

  def levels: Seq[GameUnitLevel]

  def landMoves: Option[LandMoving]

  def seaMoves: Option[WaterMoving]

  def meleeCombat: Option[MeleeCombat]

  def rangeAttack: Option[RangeAttack]

  def airBombardment: Option[AirBombardment]

  def antiAirDefence: Option[AntiAirDefence]

  def visibilityModel: VisibilityModel

  /* ability to carry units */
  def maxCarriedUnits: Int

  def cost: Int = requirements.cost

  def maintenanceCost: Int

  // fields with links to other GameRulesContent

  /** build roads, landUpgrades, cities, destroy them */
  def possibleActions: Seq[GameUnitActionType]

  def requirements: RequirementForCityProduction

  def upgradesTo: Seq[GameUnitType]
}


class GameUnitLevel(val requiredExperience: Int, val maxHitPoints: Int)

class MeleeCombat(val attack: Int,
                  val defence: Int,
                  val attacksPerMove: Int,
                  val canAttackFromWater: Boolean)

class RangeAttack(val strength: Int,
                  val range: Int,
                  val attacksPerMove: Int)

class AirBombardment(val bombardmentStrength: Int,
                     val range: Int,
                     val antiAirDefence: Int)

class AntiAirDefence(val defence: Int, val defenceRange: Int)

class WaterMoving(val moves: Int,
                  val maxDepth: Int)


/**
  * some units (like tanks or catapults) can't move in mountains without roads
  */
class LandMoving(val moves: Int,
                 val onlyOnRoads: mutable.Set[TerrainTypeView])


/**
  * model which determines which cells on map unit see.
  */
sealed trait VisibilityModel

/**
  * see own cell and 6 neighbours
  * maybe see cells on distance 2 (depends on terrain type: mountains, hills, water)
  */
case object DirectVisibility extends VisibilityModel

/**
  * see all cells with distance <= range
  *
  * @param range distance to opened cell
  */
case class RadarModel(range: Int) extends VisibilityModel


sealed trait MovingEnvironment

case object Land extends MovingEnvironment

case object Water extends MovingEnvironment

case object Air extends MovingEnvironment

class GameUnitTypeImpl(var name: String,
                       var movingOn: MovingEnvironment,
                       var isMilitary: Boolean,
                       var levels: Seq[GameUnitLevel],
                       var landMoves: Option[LandMoving],
                       var seaMoves: Option[WaterMoving],
                       var meleeCombat: Option[MeleeCombat],
                       var rangeAttack: Option[RangeAttack],
                       var airBombardment: Option[AirBombardment],
                       var antiAirDefence: Option[AntiAirDefence],
                       var visibilityModel: VisibilityModel,
                       var maxCarriedUnits: Int,
                       var maintenanceCost: Int,
                       var possibleActions: ArrayBuffer[GameUnitActionType],
                       var requirements: RequirementForCityProduction,
                       var upgradesTo: ArrayBuffer[GameUnitType]) extends GameUnitType {}


object GameUnitType extends DilatedConverter[GameUnitTypeImpl] {

  import ConfigLoader._

  override def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): GameUnitTypeImpl = {
    new GameUnitTypeImpl(
      name = config.getString("name"),
      movingOn = config.moveEnv("moveOn"),
      isMilitary = config.getBoolean("military"),
      levels = config.getConfigs("levels").map(_.level),
      landMoves = config.getOption[Config]("landMoves").map(_.landMoves),
      seaMoves = config.getOption[Config]("seaMoves").map(c => new WaterMoving(c.getInt("moves"), c.getInt("maxDepth"))),
      meleeCombat = config.getOption[Config]("meleeCombat").map(_.meleeCombat),
      rangeAttack = config.getOption[Config]("rangeAttack").map(_.rangeAttack),
      airBombardment = config.getOption[Config]("airBombardment").map(_.airBombardment),
      antiAirDefence = config.getOption[Config]("antiAirDefence").map(_.antiAirDefence),
      visibilityModel = config.getOption[Config]("visibilityModel").map(_.visibility).getOrElse(DirectVisibility),
      maxCarriedUnits = config.getOption[Int]("carriedUnits").getOrElse(0),
      maintenanceCost = config.getInt("maintenance"),
      possibleActions = new ArrayBuffer[GameUnitActionType]() ++
        config.getOption[Config]("actions").map(_.asLinked[Seq[GameUnitActionType]]).getOrElse(List.empty),
      requirements = config.asLinked[RequirementForCityProduction]("requirements"),
      upgradesTo = new ArrayBuffer[GameUnitType]()
    ) {
      this.doLate {
        upgradesTo ++= config.getStrings("upgradesTo").map(gameRules.unitTypes(_))
      }
    }
  }

  private implicit class ConfigInnerExt(val c: Config) extends AnyVal {
    def moveEnv(path: String): MovingEnvironment = c.getString(path) match {
      case "land" => Land
      case "water" => Water
      case "air" => Air
    }

    def level: GameUnitLevel = new GameUnitLevel(c.getInt("exp"), c.getInt("hp"))

    def meleeCombat: MeleeCombat = new MeleeCombat(
      c.getInt("attack"),
      c.getInt("defence"),
      c.getOption[Int]("attacksPerMove").getOrElse(1),
      c.getOption[Boolean]("canAttackFromWater").getOrElse(false)
    )

    def landMoves(implicit gameRules: GameRules, dilatedExecutor: DilatedExecutor): LandMoving = {
      new LandMoving(
        moves = c.getInt("moves"),
        onlyOnRoads = new mutable.HashSet[TerrainTypeView]()
      ) {
        this.doLate {
          onlyOnRoads ++= c.getStrings("onlyOnRoads").map(gameRules.terrainTypes(_))
        }
      }
    }

    def rangeAttack: RangeAttack = new RangeAttack(
      c.getInt("strength"),
      c.getInt("range"),
      c.getOption[Int]("attacksPerMove").getOrElse(1))

    def airBombardment: AirBombardment = new AirBombardment(
      c.getInt("strength"),
      c.getInt("range"),
      c.getInt("antiAirDefence"))

    def antiAirDefence: AntiAirDefence =
      new AntiAirDefence(defence = c.getInt("defence"), defenceRange = c.getInt("defenceRange"))

    def visibility: VisibilityModel = c.getString("type") match {
      case "eyes" => DirectVisibility
      case "radar" => RadarModel(c.getInt("distance"))
    }
  }

}
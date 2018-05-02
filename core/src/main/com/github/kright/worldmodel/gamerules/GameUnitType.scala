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

import com.github.kright.utils.HasId

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait GameUnitType extends HasId {

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

  def cost: Int

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
                 val onlyOnRoads: Seq[TerrainType])


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

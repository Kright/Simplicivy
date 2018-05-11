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

package com.github.kright.worldmodel.units

import com.github.kright.worldmodel.MapCell
import com.github.kright.worldmodel.country.CountryLink
import com.github.kright.worldmodel.gamerules.{GameUnitTypeView, NoRoad, Railroad, Road}
import com.github.kright.worldmodel.worldmap.{MapPosition, PathFinder, PlayerMapView}

/**
  * Created by Igor Slobodskov on 11 May 2018
  */
trait GameUnitView extends MapPosition {

  //may be edited py player
  def name: String

  def activity: GameUnitActivity

  val asMilitary: Option[GameUnitMilitaryView]

  val asCarrier: Option[GameUnitCarrierView]

  val unitType: GameUnitTypeView

  val owner: CountryLink

  def possibleMoves(implicit playerMapView: PlayerMapView): collection.Set[MapCell]

  def possibleActions(implicit playerMapView: PlayerMapView): Seq[PlayerMapView]
}


class GameUnit(val unitType: GameUnitTypeView, val owner: CountryLink, var x: Int, var y: Int, startXp: Int) extends GameUnitView {
  var customName: Option[String] = None

  def name: String = customName.getOrElse(unitType.name)

  private var landMoves: Int = maxLandMoves
  private var seaMoves: Int = maxSeaMoves

  var activity: GameUnitActivity = GameUnitActivity.AwaitingOrders

  val asMilitary: Option[GameUnitMilitary] =
    if (unitType.isMilitary) {
      Option(new GameUnitMilitary(this, startXp))
    } else {
      None
    }

  val asCarrier: Option[GameUnitCarrier] =
    if (unitType.maxCarriedUnits > 0) {
      Option(new GameUnitCarrier(this))
    } else {
      None
    }

  override def possibleMoves(implicit gameMap: PlayerMapView): collection.Set[MapCell] = {
    if (unitType.landMoves.isDefined) {
      return PathFinder.availableCells[MapCell](landMovementCost, landMovementNeighbors(gameMap), gameMap(this), landMoves).keySet
    }
    if (unitType.seaMoves.isDefined) {
      return PathFinder.availableCells[MapCell](_.movementCost, waterMovementNeighbors(gameMap), gameMap(this), seaMoves).keySet
    }
    ??? //todo support for air units
  }

  private def landMovementCost(cell: MapCell): Double = {
    cell.road match {
      case Railroad(maxDistance) => maxLandMoves.toDouble / maxDistance
      case Road(maxDistance) => 1.0 / maxDistance
      case NoRoad =>
        if (cell.isLand) {
          cell.movementCost
        } else { // water
          assert(cell.units.flatMap(_.asCarrier).exists(_.canBoardUnit(this)))
          10 // cost of boarding to ship
        }
    }
  }

  private def landMovementNeighbors(map: PlayerMapView)(position: MapPosition): Seq[MapCell] =
    map.neighbors(position).filter { c =>
      c.visibility.isKnown && c.mayContainUnitsOf(owner) && (c.isLand || c.units.flatMap(_.asCarrier).exists(_.canBoardUnit(this)))
    }

  /** ship can move to cell with water without enemies or to player's city */
  private def waterMovementNeighbors(map: PlayerMapView)(position: MapPosition): Seq[MapCell] =
    map.neighbors(position).filter { c =>
      c.visibility.isKnown && (c.isWater || c.city.exists(_.owner == owner)) && c.mayContainUnitsOf(owner)
    }

  override def possibleActions(implicit playerMapView: PlayerMapView): Seq[PlayerMapView] = ??? //todo

  private def maxLandMoves = unitType.landMoves.map(_.moves).getOrElse(0)

  private def maxSeaMoves = unitType.seaMoves.map(_.moves).getOrElse(0)
}

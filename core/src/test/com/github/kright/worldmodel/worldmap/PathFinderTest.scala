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

package com.github.kright.worldmodel.worldmap

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 11 May 2018
  */
@RunWith(classOf[JUnitRunner])
class PathFinderTest extends FunSuite {

  class Position(val moveCost: Double = 1) {
    val neighbors = new ArrayBuffer[Position]()

    def link(another: Position) = {
      assert(!neighbors.contains(another))

      neighbors += another
      another.neighbors += this
    }
  }

  test("world with only one position") {
    val start = new Position(1)

    val map = PathFinder.availableCells[Position](_.moveCost, _.neighbors, start, 1)

    assert(map.size == 1)
    assert(map(start) == 0)
  }

  test("can only stay with zero moves") {
    val start = new Position()
    val second = new Position()
    start link second

    val map = PathFinder.availableCells[Position](_.moveCost, _.neighbors, start, 0)

    assert(map.size == 1)
    assert(map(start) == 0)
  }

  test("two positions, 1-1") {
    val start = new Position(1)
    val second = new Position(1)
    start link second

    val map = PathFinder.availableCells[Position](_.moveCost, _.neighbors, start, 1)

    assert(map.size == 2)
    assert(map(start) == 0)
    assert(map(second) == 1)
  }

  test("two positions 1 2") {
    val start = new Position(1)
    val second = new Position(2)
    start link second

    val map = PathFinder.availableCells[Position](_.moveCost, _.neighbors, start, 2)
    assert(map.size == 2)
    assert(map(start) == 0)
    assert(map(second) == 2)
  }

  test("romb positions") {
    val start = new Position()
    val left = new Position()
    val right = new Position(3)
    val goal = new Position()
    start link left
    start link right
    goal link left
    goal link right

    val map = PathFinder.availableCells[Position](_.moveCost, _.neighbors, start, 6)

    assert(map.size == 4)
    assert(map(start) == 0)
    assert(map(left) == 1)
    assert(map(right) == 3)
    assert(map(goal) == 2)
  }

}

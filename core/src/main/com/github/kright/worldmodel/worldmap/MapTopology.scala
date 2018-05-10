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

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * Class which knows how wrap coordinates and check that them are valid
  * It can map coordinates to indices in square 2d array
  */
class MapTopology(val width: Int, val height: Int, val wrappingX: Boolean = true, val wrappingY: Boolean = false) {

  def isValid(x: Int, y: Int): Boolean = {
    if (!wrappingY && !inRange(y, height))
      return false

    if (!wrappingX && !inRange(x, width))
      return false

    true
  }

  def isValid(p: Position): Boolean = isValid(p.x, p.y)

  def distance(x1: Int, y1: Int, x2: Int, y2: Int): Int = {
    if (!wrappingX) {
      assert(inRange(x1, width))
      assert(inRange(x2, width))
    }

    if (!wrappingY) {
      assert(inRange(y1, height))
      assert(inRange(y2, height))
    }

    var innerX1 = x1
    var innerX2 = x2

    if (!wrappingX && !wrappingY) {
      //move them to one map space
      if (y1 / 2 + innerX1 >= width) innerX1 -= width
      if (y2 / 2 + innerX2 >= width) innerX2 -= width

      return distance(innerX1 - innerX2, y1 - y2)
    }

    if (!wrappingY && wrappingX) {
      innerX1 = wrap(innerX1, width)
      innerX2 = wrap(innerX2, width)
      val delta = if (innerX1 > innerX2) -width else +width
      return math.min(
        distance(innerX1 - innerX2, y1 - y2),
        distance(innerX1 - innerX2 + delta, y1 - y2))
    }

    throw new RuntimeException("other wrapping configurations isn't supported yet")
  }

  /**
    * @param dx - delta of x coordinate
    * @param dy - delta of y coordinate
    * @return if (sign(x) == sign(y)) |x| + |y| else max(|x|,|y|)
    */
  def distance(dx: Int, dy: Int): Int = {
    if (dx >= 0) {
      if (dy >= 0) dx + dy else math.max(dx, -dy)
    } else {
      if (dy <= 0) -dx - dy else math.max(-dx, dy)
    }
  }

  /** with invalid position may work incorrect! */
  def wrappedX(p: Position): Int = wrappedX(p.x, p.y)

  /** with invalid position may work incorrect! */
  def wrappedY(p: Position): Int = wrappedY(p.y)

  /** with invalid position may work incorrect! */
  def wrappedX(x: Int, y: Int): Int = {
    val wrY = wrappedY(y)
    if (wrY == y) {
      wrap(x, width)
    } else {
      wrap(x + (wrY - y) / 2, width)
    }
  }

  /** with invalid position may work incorrect! */
  def wrappedY(y: Int): Int = wrap(y, height)

  private def inRange(v: Int, max: Int): Boolean = v >= 0 && v < max

  private def wrap(x: Int, modulo: Int): Int = {
    var result = x % modulo
    if (result < 0)
      result += modulo
    result
  }
}

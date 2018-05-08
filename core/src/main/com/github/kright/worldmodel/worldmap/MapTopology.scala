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
class MapTopology(val width: Int, val height: Int, val wrapX: Boolean = true, val wrapY: Boolean = false) {

  def isValid(p: Position): Boolean = {
    if (!wrapY && !inRange(p.y, height))
      return false

    val internalX = p.x - p.y / 2
    if (!wrapX && !inRange(internalX, width))
      return false

    true
  }

  /* with invalid position may work incorrect! */
  def wrappedX(p: Position): Int = wrap(p.x, width)

  /* with invalid position may work incorrect! */
  def wrappedY(p: Position): Int = wrap(p.y, height)

  private def inRange(v: Int, max: Int): Boolean = v >= 0 && v < max

  private def wrap(x: Int, modulo: Int): Int = {
    var result = x % modulo
    if (result < 0)
      result += modulo
    result
  }
}

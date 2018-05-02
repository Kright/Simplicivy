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

package com.github.kright.utils

import scala.collection.mutable.ArrayBuffer

class Array2d[T](val width: Int, val height: Int) {

  val array = new ArrayBuffer[T](width * height)

  for (_ <- 0 until width * height) {
    array += null.asInstanceOf[T]
  }

  @inline
  private def check(x: Int, y: Int): Unit = {
    assert(x >= 0 && x < width && y >= 0 && y < height,
      s"Array2d(width=$width, height=$height) InvalidIndices($x, $y)")
  }

  @inline
  private def pos(x: Int, y: Int): Int = {
    check(x, y)
    x + width * y
  }

  @inline
  def apply(x: Int, y: Int): T = array(pos(x, y))

  @inline
  def update(x: Int, y: Int, value: T): Unit = array(pos(x, y)) = value
}

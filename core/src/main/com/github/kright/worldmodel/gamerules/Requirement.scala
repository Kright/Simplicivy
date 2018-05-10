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

import scala.collection.mutable

trait Requirement[-T] {
  def apply(t: T): Boolean

  def inverted = {
    val self = this
    new Requirement[T] {
      override def apply(t: T): Boolean = !self(t)
    }
  }
}

object Requirement {

  object AllowAll extends Requirement[Any] {
    override def apply(t: Any): Boolean = true
  }

  class ElementsFromSet[T](val set: mutable.HashSet[T]) extends Requirement[T] {
    override def apply(t: T): Boolean = set.contains(t)
  }

}

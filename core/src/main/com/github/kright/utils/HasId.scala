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

import scala.collection.mutable

/**
  * Created by Igor Slobodskov on 27 April 2018
  *
  * May be used for saving and loading data: save ids instead of links and restore them when loading
  */
trait HasId {

  def id: Int
}


trait ObjectOwner[T <: HasId] {

  def all: Iterable[T]

  def apply(id: Int): T

  def apply(i: T): T = apply(i.id)

  def contains(id: Int): Boolean

  def contains(i: T): Boolean = contains(i.id)
}


class SimpleObjectOwner[T <: HasId] extends ObjectOwner[T] {

  def this(objects: Seq[T]) {
    this()
    objects.foreach(add)
  }

  val hashMap = new mutable.HashMap[Int, T]()

  override def all: Iterable[T] = hashMap.values

  override def apply(id: Int): T = hashMap(id)

  def add(t: T): Unit = {
    assert(!contains(t))
    hashMap(t.id) = t
  }

  override def contains(id: Int): Boolean = hashMap.contains(id)
}

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

  def this(objects: Seq[T]) = this() {
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

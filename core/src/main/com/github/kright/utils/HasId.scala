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

  def apply(id: Int): T

  def apply(i: T): T = apply(i.id)
}


class SimpleObjectOwner[T <: HasId] extends ObjectOwner[T] {

  val values = new mutable.HashMap[Int, T]()

  def add(t: T): Unit = values(t.id) = t

  override def apply(id: Int): T = values(id)
}

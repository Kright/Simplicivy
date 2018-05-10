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

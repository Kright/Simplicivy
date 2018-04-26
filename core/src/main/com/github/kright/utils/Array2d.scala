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

  def apply(x: Int, y: Int): T = array(pos(x, y))

  def update(x: Int, y: Int, value: T): Unit = array(pos(x, y)) = value
}

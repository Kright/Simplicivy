package com.github.kright.utils

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Array2dTest extends FunSuite {

  val width = 3
  val height = 2

  test("new Array2d contains nulls") {
    val array2d = new Array2d[AnyRef](width, height)

    for (y <- 0 until height; x <- 0 until width)
      assert(array2d(x, y) == null)
  }

  test("exception for invalid indices") {
    val array2d = new Array2d[AnyRef](width, height)

    for ((x, y) <- Array((-1, 0), (width, 0), (0, -1), (0, height)))
      assertThrows[AssertionError](array2d(x, y))
  }

  test("assignment") {
    val array2d = new Array2d[AnyRef](width, height)
    val obj = new AnyRef()

    array2d(0, 0) = obj
    assert(array2d(0, 0) == obj)
  }
}

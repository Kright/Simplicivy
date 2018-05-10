package com.github.kright.worldmodel.worldmap

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TopologyTest extends FunSuite {

  test("coordinates validity") {
    val w = 6
    val h = 4

    new MapTopology(w, h, wrappingX = true, wrappingY = true) {
      assert(isValid(0, 0))
      assert(isValid(w, 0))
      assert(isValid(0, h))
      assert(isValid(w, h))
      assert(isValid(-1, 0))
      assert(isValid(0, -1))
      assert(isValid(-1, -1))
    }

    new MapTopology(w, h, wrappingX = false, wrappingY = false) {
      assert(isValid(0, 0))
      assert(isValid(w - 1, 0))
      assert(isValid(0, h - 1))
      assert(isValid(w - 1, h - 1))

      assert(!isValid(-1, 0))
      assert(!isValid(0, -1))
      assert(!isValid(-1, -1))
      assert(!isValid(w, 0))
      assert(!isValid(0, h))
    }

    new MapTopology(w, h, wrappingX = true, wrappingY = false) {
      assert(isValid(0, 0))
      assert(isValid(w - 1, 0))
      assert(isValid(0, h - 1))
      assert(isValid(w - 1, h - 1))

      assert(isValid(-1, 0))
      assert(isValid(w, 0))

      assert(!isValid(0, -1))
      assert(!isValid(-1, -1))
    }
  }

  test("distance") {
    val w = 6
    val h = 4

    new MapTopology(w, h, wrappingX = false, wrappingY = false) {
      assert(distance(0, 0, w - 1, 0) == w - 1)
      assert(distance(0, 0, 0, h - 1) == h - 1)

      assert(distance(0, 2, w - 1, 2) == 1)
      assert(distance(w - 1, 2, w - 2, 2) == w - 1)
      assert(distance(0, h - 1, w - 1, h - 1) == 1)
    }
  }
}

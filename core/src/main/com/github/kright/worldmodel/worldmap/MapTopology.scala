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
  @inline
  def wrappedX(p: Position): Int = wrap(p.x, width)

  /* with invalid position may work incorrect! */
  @inline
  def wrappedY(p: Position): Int = wrap(p.y, height)

  @inline
  private def inRange(v: Int, max: Int): Boolean = v >= 0 && v < max

  @inline
  private def wrap(x: Int, modulo: Int): Int = {
    var result = x % modulo
    if (result < 0)
      result += modulo
    result
  }
}

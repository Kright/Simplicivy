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

/**
  * Created by Igor Slobodskov on 05 May 2018
  */
class FPSMeasure {

  private def now(): Long = System.nanoTime()

  private var prev: Long = now()
  private var t: Long = now()
  private var dt: Long = 0
  private var maxDt: Long = 0

  private var ticks: Int = 0
  private var framesPerSec: Int = 0

  def tick(): Unit = {
    ticks += 1
    val current = now()
    dt = current - t
    t = current
    val oneSecond = 1000000000L
    if (current - prev > oneSecond) {
      framesPerSec = ticks
      ticks = 0
      prev = current
      maxDt = 0
    }

    if (dt > maxDt) {
      maxDt = dt
    }
  }

  def fps: Int = framesPerSec

  def maxDeltaNs: Long = maxDt
}

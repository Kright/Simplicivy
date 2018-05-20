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

import com.badlogic.gdx.math.{Vector => LibgdxVector}
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
object LibgdxExt {

  implicit class VectorExt[Vec <: LibgdxVector[Vec]](val a: Vec) extends AnyVal {

    def :=(value: Vec): Unit = a.set(value)

    def +=(delta: Vec): Unit = a.add(delta)

    def -=(delta: Vec): Unit = a.sub(delta)

    def *=(mul: Float): Unit = a.scl(mul)

    def *=(v: Vec): Unit = a.scl(v)

    def /=(f: Float): Unit = a *= (1f / f)

    // create new objects

    def +(b: Vec): Vec = a.cpy().add(b)

    def -(b: Vec): Vec = a.cpy().sub(b)

    def *(mul: Float): Vec = a.cpy().scl(mul)

    def *(v: Vec): Vec = a.cpy().scl(v)

    def /(div: Float): Unit = a.cpy().scl(1f / div)

    def ==(v: Vec): Boolean = a.epsilonEquals(v, 0.000001f)

    def !=(v: Vec): Boolean = a != v
  }

  def schedule(delaySeconds: Float)(action: => Unit): Unit =
    Timer.schedule(new Task {
      override def run(): Unit = {
        action
      }
    }, delaySeconds)
}

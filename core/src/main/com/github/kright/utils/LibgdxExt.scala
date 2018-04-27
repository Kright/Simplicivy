package com.github.kright.utils

import com.badlogic.gdx.math.{Vector => LibgdxVector}

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
object LibgdxExt {

  implicit class VectorExt[Vec <: LibgdxVector[Vec]](val a: Vec) extends AnyVal {

    // syntax sugar
    // todo compare compiled bytecode with @inline and without

    def :=(value: Vec): Unit = a.set(value)

    def +=(delta: Vec): Unit = a.add(delta)

    def -=(delta: Vec): Unit = a.sub(delta)

    def *=(mul: Float): Unit = a.scl(mul)

    def *=(v: Vec): Unit = a.scl(v)

    @inline
    def /=(f: Float): Unit = a *= (1f / f)

    // create new objects

    def +(b: Vec): Vec = a.cpy().add(b)

    def -(b: Vec): Vec = a.cpy().sub(b)

    def *(mul: Float): Vec = a.cpy().scl(mul)

    def *(v: Vec): Vec = a.cpy().scl(v)

    @inline
    def /(div: Float): Unit = a.cpy().scl(1f / div)
  }

}
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

package com.github.kright.worldmodel.gamerules


import com.typesafe.config.Config

import scala.util.Try

/**
  * Created by Igor Slobodskov on 02 May 2018
  */
object ConfigLoader {

  /*
  требования к конфигу: по возможности простой код
  линковка данных
  вывод понятных ошибок, если что-то не так.
   */

  implicit class ConfigExt(val config: Config) extends AnyVal {
    def as[T](implicit converter: ConfigConverter[T]): Either[T, ConfigError] = converter.convert(config)
  }

  def test() = {

    def str =
      """
        |listA = [
        |   { name = "a0", a = 0}
        |   { name = "a1", a = 1}
        |]
        |
        |b = {name = 2, a = a0}
      """.stripMargin

    println("Hello world!")
  }
}

class A(a: Int, name: String)

class B(a: A, name: String)

class ConfigError(reason: String) extends RuntimeException

trait ConfigReader[A] {
  def read(config: Config, path: String): Option[A]
}

trait ConfigConverter[A] {
  def convert(config: Config): Either[A, ConfigError]
}

private class Binding[T](val setter: (T) => Unit, val name: String) {
  def bind(mapping: (String) => T): Unit = setter(mapping(name))
}

object ConfigReader {
}
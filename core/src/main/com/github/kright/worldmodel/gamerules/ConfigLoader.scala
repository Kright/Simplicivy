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


import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 02 May 2018
  */
object ConfigLoader {

  import LinksSet._

  implicit class ConfigExt(val config: Config) extends AnyVal {
    def as[T](implicit converter: ConfigConverter[T]): T = converter.convert(config)

    def link[T <: HasName](name: String)(implicit linkSet: LinksSet, toHolder: (LinksSet) => LinkHolder[T]): T = {
      linkSet.link(name, config)
    }

    def asLinked[T <: HasName](implicit converter: LinkingConverter[T], linksSet: LinksSet): T =
      converter.convert(config, linksSet)
  }

  implicit val converterA: ConfigConverter[A] = new ConfigConverter[A] {
    override def convert(config: Config): A = new A(config.getInt("a"), config.getString("name"))
  }

  implicit class HasNameExt[T <: HasName](val a: T) extends AnyVal {
    def doLate(func: => Unit)(implicit linkSet: LinksSet): Unit = {
      linkSet.addCallback { () => func }
    }
  }

  implicit val converterB: LinkingConverter[B] = new LinkingConverter[B] {
    override def convert(implicit config: Config, linksSet: LinksSet): B = {
      new B(null, config.getString("name")) {
        this.doLate {
          a = config.link[A]("a")
        }
      }
    }
  }

  def test(): Unit = {

    def str =
      """
        |gameRules {
        |  listA = [
        |    { name = "a0", a = 0}
        |    { name = "a1", a = 1}
        |  ]
        |
        |  listB = [
        |   {name = b0, a = a0}
        |   {name = b1, a = a1}
        |  ]
        |}
      """.stripMargin


    implicit val linkSet: LinksSet = new LinksSet()

    val config = ConfigFactory.parseString(str)
    val gameRules = config.getConfig("gameRules")
    val listA = gameRules.getConfigList("listA").asScala.map(_.as[A])
    val listB = gameRules.getConfigList("listB").asScala.map(_.asLinked[B])

    linkSet.linksA.addValues(listA)
    linkSet.linksB.addValues(listB)

    linkSet.resolveLinks()

    println(listA.mkString(","))
    println(listB.mkString(","))

    println("Hello world!")
  }
}

class A(var a: Int, var name: String) extends HasName {
  override def toString: String = s"A($a,$name)"
}

class B(var a: A, var name: String) extends HasName {
  override def toString: String = s"B($a,$name)"
}

class LinkHolder[T <: HasName] {
  val values = new mutable.HashMap[String, T]()

  def link(name: String, config: Config): T = values(config.getString(name))

  def addValues(vv: Seq[T]): Unit = vv.foreach { v => values(v.name) = v }
}

class LinksSet {
  val linksA = new LinkHolder[A]
  val linksB = new LinkHolder[B]

  def linkA(s: String): A = linksA.values(s)

  def linkB(s: String): B = linksB.values(s)

  private val callBacks = new ArrayBuffer[() => Unit]()

  def addCallback(func: () => Unit): Unit = callBacks += func

  def resolveLinks(): Unit = callBacks.foreach(f => f())
}

object LinksSet {
  implicit def toHolderA(linksSet: LinksSet): LinkHolder[A] = linksSet.linksA

  implicit def toHolderB(linksSet: LinksSet): LinkHolder[B] = linksSet.linksB
}

trait LinkingConverter[T <: HasName] {
  def convert(implicit config: Config, linksSet: LinksSet): T
}

abstract class ConfigReader[A] {
  def read(config: Config, path: String): Option[A]
}

trait ConfigConverter[T] {
  def convert(config: Config): T
}

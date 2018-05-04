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
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by Igor Slobodskov on 04 May 2018
  */
@RunWith(classOf[JUnitRunner])
class ConfigLoaderTest extends FunSuite {

  import ConfigLoader._

  def parse(s: String): Config = ConfigFactory.parseString(s)

  test("load CellProduction") {
    val cp = ConfigFactory
      .parseString("food = 2, commerce = 3, production = 4")
      .as[MutableCellProduction]

    assert(cp.food == 2)
    assert(cp.commerce == 3)
    assert(cp.production == 4)
  }

  test("load BuildingEffectImpl") {
    val cp = ConfigFactory.parseString(
      """
        |culture = 1
        |corruptionDecrease = 2
        |maintenance = 3
        |defenceBonus = 4
        |happiness = 5
        |pollution = 6
        |productionBonus = 7
        |researchBonus = 8
        |taxBonus = 9
      """.stripMargin).as[BuildingEffectImpl]

    assert(cp.culture == 1)
    assert(cp.corruptionDecrease == 2)
    assert(cp.maintenance == 3)
    assert(cp.defenceBonus == 4)
    assert(cp.happiness == 5)
    assert(cp.pollution == 6)
    assert(cp.productionBonus == 7)
    assert(cp.researchBonus == 8)
    assert(cp.taxBonus == 9)
  }

  test("load empty BuildingEffectImpl") {
    val cp = ConfigFactory.parseString("").as[BuildingEffectImpl]

    assert(cp.culture == 0)
    assert(cp.corruptionDecrease == 0)
    assert(cp.maintenance == 0)
    assert(cp.defenceBonus == 0)
    assert(cp.happiness == 0)
    assert(cp.pollution == 0)
    assert(cp.productionBonus == 0)
    assert(cp.researchBonus == 0)
    assert(cp.taxBonus == 0)
  }

  test("load TerrainType") {
    val t = parse("name = grass, isLand=true, height = 1, movementCost = 2, defenceBonus = 3, produces = {food = 4, production = 5, commerce = 6}").as[TerrainTypeImpl]

    assert(t.name == "grass")
    assert(t.isLand == true)
    assert(t.height == 1)
    assert(t.movementCost == 2)
    assert(t.defenceBonus == 3)
    assert(t.produces.food == 4)
    assert(t.produces.production == 5)
    assert(t.produces.commerce == 6)
  }

}

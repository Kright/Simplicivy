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

import com.typesafe.config.ConfigFactory
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by Igor Slobodskov on 04 May 2018
  */
@RunWith(classOf[JUnitRunner])
class ConfigLoaderBigTest extends FunSuite {

  def text =
    """
      |gameRules {
      |   technologies = [
      |      { name = simple, cost = 5}
      |      { name = military, cost = 10, requiredTechnologies = [simple]}
      |      { name = peaceful, cost = 10, requiredTechnologies = [simple]}
      |      { name = complex, cost = 20, requiredTechnologies = [military, peaceful]}
      |   ]
      |
      |   terrainTypes = [
      |      {
      |         name = grass
      |         isLand = true
      |         height = 1
      |         movementCost = 1
      |         produces {food: 2, production: 1, commerce: 1}
      |      }
      |      {
      |         name = sea,
      |         isLand = false
      |         height = -2
      |         produces {food: 1, commerce: 1}
      |      }
      |      {
      |         name = hills
      |         isLand = true
      |         height = 2
      |         movementCost = 2
      |         defenceBonus = 1
      |         produces {food: 1, production: 2, commerce: 2}
      |      }
      |   ]
      |
      |   resources = [
      |     {name = fish, kind=bonus, bonus {food:3}, terrain = [sea]}
      |     {name = iron, kind=strategic, bonus {production: 3}, terrain = [hills], technology = military}
      |     {name = fur, kind=luxury, bonus {food: 1, commerce: 2}, terrain = [hills, grass]}
      |   ]
      |
      |   cityBuildings = [
      |     {
      |       name = walls,
      |       requires = {
      |         cost = 10
      |         technology = [military, peaceful]
      |       }
      |       effects {maintenance: 2, defence: 2}
      |     }
      |     {
      |       name = watermill
      |       requires = {
      |         cost = 10
      |         technology = [complex]
      |         requireRiver = true
      |       }
      |       effects {maintenance: 1, productionBonus: 1}
      |     }
      |     {
      |       name = library
      |       requires = {
      |         cost = 20
      |         technology = simple
      |       }
      |       effects {culture: 3, happiness: 1, maintenance: 2}
      |     }
      |     {
      |       name = university
      |       requires = {
      |         cost = 40
      |         technology = complex
      |         cityBuildings = library
      |       }
      |       effects = {culture: 5, happiness: 2, maintenance: 3}
      |     }
      |   ]
      |
      |   landUpgrades = [
      |     {name = farm, resources = [fur]}
      |     {name: castle}
      |   ]
      |
      |   standardLevels = [{exp: 0, hp: 2}, {exp: 2, hp: 3}, {exp: 10, hp: 4}, {exp: 20, hp: 5}]
      |
      |   units = [
      |     {
      |       name = warrior
      |       moveOn = land
      |       military = true
      |       levels = ${standardLevels}
      |       landMoves = {moves = 1}
      |       meleeCombat = {attack = 1, defence = 1}
      |       requirements = {cost = 5}
      |       maintenance = 1
      |       upgradesTo = swordsman
      |     }
      |     {
      |       name = swordsman
      |       moveOn = land
      |       military = true
      |       levels = ${standardLevels}
      |       landMoves = {moves = 1}
      |       meleeCombat = {attack = 3, defence = 2}
      |       maintenance = 1
      |       requirements = {
      |         cost = 10
      |         technology = military
      |         resources = iron
      |       }
      |     }
      |     {
      |       name = boat
      |       moveOn = water
      |       military = true
      |       levels = ${standardLevels}
      |       seaMoves = {moves = 3, maxDepth = 1}
      |       meleeCombat = {attack = 1, defence = 1}
      |       rangeAttack = {strength = 1, range: 1}
      |       carriedUnits = 1
      |       maintenance = 2
      |       requirements = {
      |         cost = 30
      |         technology = simple
      |         requireSea = true
      |       }
      |     }
      |   ]
      |
      |   nations = [
      |     {
      |        name = UK
      |        features {
      |           seaExpansion = true
      |           moreCommerce = true
      |        }
      |        startingTechs = [simple]
      |     }
      |     {
      |       name = Roma
      |       features {
      |         military = true
      |         landExpansion = true
      |       }
      |     }
      |     {
      |       name = Greece
      |       features {
      |         cultural = true
      |         scientific = true
      |       }
      |       startingTechs = [simple, peaceful]
      |     }
      |   ]
      |}
      |
    """.stripMargin

  test("text are valid") {
    ConfigFactory.parseString(text)
  }

  test("creating game rules") {
    val rules = GameRules.convert(ConfigFactory.parseString(text).getConfig("gameRules").resolve())
  }

  test("checking game rules") {
    val rules = GameRules.convert(ConfigFactory.parseString(text).getConfig("gameRules").resolve())

    assert(rules.technologies.all.size == 4)
    assert(rules.nations.all.size == 3)
    assert(rules.unitTypes.all.size == 3)
    assert(rules.langUpgradeTypes.all.size == 2)
    assert(rules.cityBuildings.all.size == 4)
    assert(rules.resources.all.size == 3)
    assert(rules.terrainTypes.all.size == 3)

    assert(rules.technologies("military").requiredTechnologies(0).name == "simple")
  }
}

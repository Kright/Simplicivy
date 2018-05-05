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

  def text: String =
    """
      |gameRules {
      |   technologies {
      |      simple { cost = 5}
      |      military { cost = 10, requiredTechnologies = [simple]}
      |      peaceful { cost = 10, requiredTechnologies = [simple]}
      |      complex = { cost = 20, requiredTechnologies = [military, peaceful]}
      |   }
      |
      |   terrainTypes {
      |      grass {
      |         isLand = true
      |         height = 1
      |         movementCost = 1
      |         produces {food: 2, production: 1, commerce: 1}
      |      }
      |      sea {
      |         isLand = false
      |         height = -2
      |         produces {food: 1, commerce: 1}
      |      }
      |      hills {
      |         isLand = true
      |         height = 2
      |         movementCost = 2
      |         defenceBonus = 1
      |         produces {food: 1, production: 2, commerce: 2}
      |      }
      |   }
      |
      |   resources {
      |     fish = {kind=bonus, bonus {food:3}, terrain = [sea]}
      |     iron = {name = iron, kind=strategic, bonus {production: 3}, terrain = [hills], technology = military}
      |     fur = {kind=luxury, bonus {food: 1, commerce: 2}, terrain = [hills, grass]}
      |   }
      |
      |   cityBuildings = {
      |     walls {
      |       requires {
      |         cost = 10
      |         technology = [military, peaceful]
      |       }
      |       effects {maintenance: 2, defence: 2}
      |     }
      |     watermill {
      |       requires {
      |         cost = 10
      |         technology = complex
      |         requireRiver = true
      |       }
      |       effects {maintenance: 1, productionBonus: 1}
      |     }
      |     library {
      |       requires {
      |         cost = 20
      |         technology = simple
      |       }
      |       effects {culture: 3, happiness: 1, maintenance: 2}
      |     }
      |     university {
      |       requires = {
      |         cost = 40
      |         technology = complex
      |         cityBuildings = library
      |       }
      |       effects = {culture: 5, happiness: 2, maintenance: 3}
      |     }
      |   }
      |
      |   landUpgrades = {
      |     farm = {resources = [fur]}
      |     castle = {}
      |   }
      |
      |   standardLevels = [{exp: 0, hp: 2}, {exp: 2, hp: 3}, {exp: 10, hp: 4}, {exp: 20, hp: 5}]
      |
      |   units = {
      |     settler {
      |       moveOn = land
      |       military = false
      |       maintenance = 0
      |       actions {
      |         buildCity {}
      |       }
      |       requirements = {
      |         cost: 20
      |         citizens: 2
      |       }
      |     }
      |     worker {
      |       moveOn = land
      |       military = false
      |       maintenance = 1
      |       landMoves = {moves = 1}
      |       actions {
      |         destroy {}
      |         buildRoad = [
      |           {moves:3, require {terrain = grass}}
      |           {moves:5, require {terrain = hills}}
      |         ]
      |         terraforming = [
      |           { to:grass, moves: 10, require {terrain: hills, technology = complex }}
      |         ]
      |         upgradeLand = [
      |           {
      |             upgrade: farm,
      |             moves = 5,
      |             require { terrain = [grass, hills], technology = peaceful}
      |           }
      |           {
      |             upgrade: castle
      |             moves = 10
      |             require { terrain = [grass, hills], technology = military}
      |           }
      |         ]
      |       }
      |       requirements = {
      |         cost = 10
      |         citizens = 1
      |       }
      |     }
      |     warrior = {
      |       moveOn = land
      |       military = true
      |       levels = ${standardLevels}
      |       landMoves = {moves = 1}
      |       meleeCombat = {attack = 1, defence = 1}
      |       requirements = {cost = 5, citizens:1}
      |       maintenance = 1
      |       actions { destroy{} }
      |       upgradesTo = swordsman
      |     }
      |     swordsman = ${units.warrior} {
      |       meleeCombat = {attack = 3, defence = 2}
      |       requirements = {
      |         cost = 10
      |         technology = military
      |         resources = iron
      |       }
      |       upgradesTo = null
      |     }
      |     boat {
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
      |   }
      |
      |   nations {
      |     UK {
      |        features {
      |           seaExpansion = true
      |           moreCommerce = true
      |        }
      |        startingTechs = simple
      |     }
      |     Roma {
      |       features {
      |         military = true
      |         landExpansion = true
      |       }
      |     }
      |     Greece {
      |       features {
      |         cultural = true
      |         scientific = true
      |       }
      |       startingTechs = [simple, peaceful]
      |     }
      |   }
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
    assert(rules.unitTypes.all.size == 5)
    assert(rules.langUpgradeTypes.all.size == 2)
    assert(rules.cityBuildings.all.size == 4)
    assert(rules.resources.all.size == 3)
    assert(rules.terrainTypes.all.size == 3)

    assert(rules.technologies("military").requiredTechnologies(0).name == "simple")
  }
}

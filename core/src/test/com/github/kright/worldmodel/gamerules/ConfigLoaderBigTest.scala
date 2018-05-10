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
      |   bioms {
      |      grassland {
      |         land = true
      |         movementCost = 1
      |         defenceBonus = 0
      |         produces {food: 2, production: 1, commerce: 1}
      |      }
      |      sea {
      |         water = true
      |         height = -2
      |         produces {food: 1, commerce: 1}
      |      }
      |      desert {
      |         produces {food: 0, production: 1, commerce: 0}
      |      }
      |      snow {
      |         produces {food: 1, production: 1}
      |      }
      |   }
      |
      |   terrainModifiers {
      |      forest {
      |         produces {production: 1, commerce: 1}
      |         addMoveCost = 1
      |         addDefenceBonus = 2
      |         require{ bioms = [grassland, snow] }
      |      }
      |      oasis {
      |         produces {food: 3, production: 1, commerce: 1}
      |         require {}
      |      }
      |      rifs {
      |         addMoveCost = 4
      |         addDefenceBonus = 1
      |         require {onWater = true}
      |      }
      |      floodplain {
      |         produces {food: 2}
      |         require {
      |             onlyHeight = 0
      |             onLand = true
      |             river = true
      |         }
      |      }
      |   }
      |
      |   resources {
      |     fish = {kind=bonus, bonus {food:3}, bioms = sea}
      |     iron = {name = iron, kind=strategic, bonus {production: 3}, terrain = [grassland, desert, snow], technology = military}
      |     fur = {kind=luxury, bonus {food: 1, commerce: 2}, terrain = [grassland]}
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
      |   landUpgrades {
      |     farm {
      |         resources = [fur],
      |         production {foodMultiplier:2, commerceMultiplier:2}
      |     }
      |     castle = {
      |         defenceBonus = 5
      |     }
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
      |       require {
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
      |       require = {
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
      |       require = {cost = 5, citizens:1}
      |       maintenance = 1
      |       actions { destroy{} }
      |       upgradesTo = swordsman
      |     }
      |     swordsman = ${units.warrior} {
      |       meleeCombat = {attack = 3, defence = 2}
      |       require = {
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
      |       require = {
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

    assert(rules.technologies("military").requiredTechnologies(0).name == "simple")
  }
}

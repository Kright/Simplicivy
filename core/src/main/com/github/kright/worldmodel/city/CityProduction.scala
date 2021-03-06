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

package com.github.kright.worldmodel.city

/**
  * Created by Igor Slobodskov on 22 May 2018
  */
trait CityProductionView {
  def production: Int

  def food: Int

  def commerce: Int

  def culture: Int

  def science: Int
}

class CityProduction(var production: Int = 0,
                     var food: Int = 0,
                     var commerce: Int = 0,
                     var culture: Int = 0,
                     var science: Int = 0) extends CityProductionView

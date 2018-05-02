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

package com.github.kright.worldmodel

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * cell may be:
  * unknown (black, no information)
  * visible (you see all on it)
  * shadowed (it was visible before, and you see old state)
  */
sealed trait CellVisibility {
  def isKnown: Boolean
}

case object Visible extends CellVisibility {
  override def isKnown: Boolean = true
}

case object Shadowed extends CellVisibility {
  override def isKnown: Boolean = true
}

case object Unknown extends CellVisibility {
  override def isKnown: Boolean = false
}

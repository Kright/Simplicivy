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

package com.github.kright.worldmodel.command

import com.github.kright.worldmodel.GameWorld
import com.github.kright.worldmodel.country.Country

/**
  * Game commands like "move unit", "build city", "declare war" and etc
  *
  * all comands may be reverted (if it was player error)
  *
  * Created by Igor Slobodskov on 20 May 2018
  */
trait Command {
  def doAction(gameWorld: GameWorld): CommandResult
}

object Command {
  def apply(func: GameWorld => CommandResult): Command = new Command {
    override def doAction(gameWorld: GameWorld): CommandResult = func(gameWorld)
  }
}


sealed trait CommandResult

case object CommandInvalid extends CommandResult

/**
  * may be called only once
  */
trait UndoCommand extends CommandResult {

  private var wasCalled = false

  /** return false if something gone wrong */
  final def undo(): Boolean = {
    assert(!wasCalled)

    wasCalled = true
    undoCommand()
  }

  protected def undoCommand(): Boolean
}

object UndoCommand {

  def apply(action: => Boolean): UndoCommand = new UndoCommand {
    override protected def undoCommand(): Boolean = action
  }

  val empty = UndoCommand(true)
}

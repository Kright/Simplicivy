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

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Igor Slobodskov on 20 May 2018
  */
class ComplexCommand(commands: Seq[Command]) extends Command {
  assert(commands.nonEmpty)

  val initiator: Country = commands.head.initiator

  override def doAction(gameWorld: GameWorld): CommandResult = {
    val finished = new ArrayBuffer[UndoCommand]()

    if (appendActions(gameWorld, finished)) {
      UndoCommand {
        finished.reverseIterator.forall(_.undo())
      }
    } else {
      //undo all finished sub-commands
      for (c <- finished.reverseIterator) {
        val success = c.undo()
        assert(success)
      }
      CommandInvalid
    }
  }

  private def appendActions(gameWorld: GameWorld, finished: ArrayBuffer[UndoCommand]): Boolean = {
    for (c <- commands) {
      c.doAction(gameWorld) match {
        case CommandInvalid => return false
        case undo: UndoCommand => finished += undo
      }
    }
    true
  }
}

object ComplexCommand {
  def apply(commands: Command*): Command = new ComplexCommand(commands)
}

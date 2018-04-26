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

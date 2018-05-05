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

package com.github.kright.gui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Skin}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.github.kright.utils.FPSMeasure

import com.github.kright.utils.LibgdxExt.VectorExt

/**
  * Created by Igor Slobodskov on 05 May 2018
  */
class FPSLabel(fps: FPSMeasure, screenSize: Vector2) extends Label("fps", FPSLabel.skin) {
  setAlignment(Align.left)
  private val font = FPSLabel.labelStyle.font
  private val oldScreenSize = new Vector2()

  var padding = 8

  override def act(delta: Float): Unit = {
    setText(s"fps: ${fps.fps}, max dt:${fps.maxDeltaNs / 1000}")
    if (oldScreenSize != screenSize) {
      oldScreenSize := screenSize
      setPosition(padding, screenSize.y - getHeight - padding)
    }
    super.act(delta)
  }
}

object FPSLabel {
  val labelStyle = new LabelStyle(new BitmapFont(), Color.BLACK)

  val skin = new Skin()
  skin.add("default", labelStyle)
}

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

package com.github.kright.gui.mobile


import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScreenViewport

/**
  * Created by Igor Slobodskov on 05 May 2018
  */
class MainScreen extends ScreenAdapter {
  private val texture: Texture = new Texture("badlogic.jpg")
  private val image: Image = new Image(texture) {
    setScaling(Scaling.none)
  }
  private val mainViewport = new ScreenViewport()
  private val stage = new Stage(mainViewport) {
    addActor(image)
  }

  private def now = System.currentTimeMillis()

  private val start = now

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    val phi = (now - start) * 0.001

    image.setPosition(100 * (1 + Math.sin(phi).toFloat), 100 * (1 + Math.cos(phi).toFloat))

    stage.act(delta)
    stage.draw()
  }

  override def resize(width: Int, height: Int): Unit = {
    stage.getViewport.update(width, height, true)
  }

  override def dispose(): Unit = {
    texture.dispose()
    stage.dispose()
  }
}

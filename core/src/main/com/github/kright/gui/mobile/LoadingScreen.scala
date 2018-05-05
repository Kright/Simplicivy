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

import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Timer.Task
import com.badlogic.gdx.utils.{Scaling, Timer}
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Gdx, ScreenAdapter}
import com.github.kright.MainGame

/**
  * Created by Igor Slobodskov on 05 May 2018
  */
class LoadingScreen extends ScreenAdapter {

  private val viewport = new FitViewport(16, 9)

  private val loadingStage = new Stage(viewport) {
    val backgroundImg = new Image(new Texture("badlogic.jpg")) {
      setFillParent(true)
      setScaling(Scaling.fill)
    }
    addActor(backgroundImg)
  }

  Timer.schedule(new Task {
    override def run(): Unit = MainGame.setScreen(new MainScreen())
  }, 3f)

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(loadingStage)
  }

  override def resize(width: Int, height: Int): Unit = {
    println(s"resize($width,$height)")
    viewport.apply()
    loadingStage.getViewport.update(width, height)
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    loadingStage.act(delta)
    loadingStage.draw()
  }
}

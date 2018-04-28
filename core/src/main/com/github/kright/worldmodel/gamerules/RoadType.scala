package com.github.kright.worldmodel.gamerules

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
sealed trait RoadType

case object NoRoad extends RoadType

/**
  * @param movesMultiplier how much faster units move on roads
  */
case class Road(movesMultiplier: Int) extends RoadType

/**
  * @param maxDistance max distance for moving on railroad
  *                    distance doesn't depends on moves count of unit
  */
case class Railroad(maxDistance: Int) extends RoadType

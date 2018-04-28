package com.github.kright.worldmodel.gamerules

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
sealed trait RoadType

case object NoRoad extends RoadType

case object Road extends RoadType

case object Railroad extends RoadType

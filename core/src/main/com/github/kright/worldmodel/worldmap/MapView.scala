package com.github.kright.worldmodel.worldmap

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * view of Map, which allows only observation
  */
trait MapView[+T] {
  implicit val topology: MapTopology

  def apply(p: MapPosition): T

  def apply(p: Position): T = apply(p.asMapPosition)
}

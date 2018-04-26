package com.github.kright.worldmodel.worldmap

import com.github.kright.utils.Array2d
import com.github.kright.worldmodel.{MapCell, MutableMapCell, UnknownMapCell}

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * Base map view. Only ground truth values, this map doesn't content unknown and shadowed cells
  */
class SimpleMap(implicit val topology: MapTopology) extends MapView[MutableMapCell] {

  private val table = new Array2d[MutableMapCell](topology.width, topology.height)

  override def apply(p: MapPosition): MutableMapCell = table(p.x, p.y)

  def update(p: MapPosition, value: MutableMapCell): Unit = table(p.x, p.y) = value

  def makePlayerView(): PlayerMapView = {
    val array2d = new Array2d[MapCell](topology.width, topology.height)

    for (y <- 0 until topology.height; x <- 0 until topology.width)
      array2d(x, y) = new UnknownMapCell(x, y)

    new PlayerMapView(this, array2d)
  }
}

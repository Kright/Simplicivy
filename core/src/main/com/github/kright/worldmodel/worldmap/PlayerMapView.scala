package com.github.kright.worldmodel.worldmap

import com.github.kright.utils.Array2d
import com.github.kright.worldmodel.MapCell

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * Player's own map view (is stores cells which he visited)
  */
class PlayerMapView(private val simpleMap: SimpleMap,
                    private val table: Array2d[MapCell]) extends MapView[MapCell] {

  implicit val topology: MapTopology = simpleMap.topology

  assert(table.height == topology.height)
  assert(table.width == topology.width)

  override def apply(p: MapPosition): MapCell = table(p.x, p.y)

  @inline
  private def update(p: MapPosition, value: MapCell): Unit = table(p.x, p.y) = value

  /* open cell and return it */
  def openCell(p: MapPosition): MapCell = {
    val opened = simpleMap(p)
    this (p) = opened
    opened
  }

  /* make cell shadowed and return it */
  def shadowCell(p: MapPosition): MapCell = {
    val old = this (p)
    assert(old.visibility.isKnown)
    val shadowed = old.getShadowed()
    this (p) = shadowed
    shadowed
  }
}

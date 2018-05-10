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

package com.github.kright.worldmodel.worldmap

import scala.collection.mutable

//todo test it
//todo may be priorities are wrong and need to be inverted
object PathFinder {

  /**
    * if cost of move is 2 and unit have only 1 maxDistance, it will move to it
    *
    * @param getCost      position => integer cost for moving to this position
    * @param getNeighbors position => neighbor positions
    * @param start        initial position
    * @param maxDistance  max moves count
    * @tparam T position type
    * @return map Position -> distance
    */
  @inline
  def availableCells[T](getCost: T => Double,
                        getNeighbors: T => Seq[T],
                        start: T, maxDistance: Double): mutable.HashMap[T, Double] = {
    val distances = new mutable.HashMap[T, Double]()
    val unprocessed = new mutable.PriorityQueue[T]()(Ordering.by[T, Double](distances))

    distances(start) = 0
    unprocessed += start

    while (unprocessed.nonEmpty) {
      val nearest = unprocessed.dequeue()
      val dist = distances(nearest)
      if (dist <= maxDistance) {
        getNeighbors(nearest).filterNot(distances.contains).foreach { n =>
          distances(n) = dist + getCost(n)
          unprocessed += n
        }
      }
    }

    distances
  }
}

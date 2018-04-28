package com.github.kright.worldmodel.gamerules

/**
  * Created by Igor Slobodskov on 28 April 2018
  */
trait CellProduction {

  def food: Int

  def production: Int

  def commerce: Int
}

class MutableCellProduction(var food: Int,
                            var production: Int,
                            var commerce: Int) extends CellProduction

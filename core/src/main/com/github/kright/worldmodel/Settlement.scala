package com.github.kright.worldmodel

/**
  * Created by Igor Slobodskov on 26 April 2018
  */
trait Settlement extends MapPosition{

  def name: String

  def citizensCount: Int
}

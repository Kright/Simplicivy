package com.github.kright.worldmodel.units

/**
  * Created by Igor Slobodskov on 26 April 2018
  *
  * todo something:
  *
  * units may be military and non-military (workers)
  * units may be overland, ships and flying
  *
  * overland and ship units in addition might fire to several distance
  * some of them (cannons) can't fight in closer fight
  */
trait GameUnitType {

  def name: String

  def military: Boolean


  def movementPoints: Int

  def attackPoints: Int


  def attack: Int

  def defence: Int

  def rangedAttack: Int

  def rangedAttackRange: Int
}

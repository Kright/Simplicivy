package com.github.kright.worldmodel.gamerules

import com.github.kright.utils.{ObjectOwner, SimpleObjectOwner}
import com.github.kright.worldmodel.city.CityBuilding
import com.github.kright.worldmodel.science.TechnologyDescription
import com.github.kright.worldmodel.units.GameUnitType

/**
  * Created by Igor Slobodskov on 27 April 2018
  */
class GameRulesImpl(val allTechnologies: ObjectOwner[TechnologyDescription],
                    val cityBuildings: ObjectOwner[CityBuilding],
                    val unitTypes: ObjectOwner[GameUnitType]) extends GameRules {

  def this(allTechnologies: Seq[TechnologyDescription],
           cityBuildings: Seq[CityBuilding],
           unitTypes: Seq[GameUnitType]) =
    this(new SimpleObjectOwner(allTechnologies),
      new SimpleObjectOwner(cityBuildings),
      new SimpleObjectOwner(unitTypes)
    )
}

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

package com.github.kright.worldmodel.gamerules


import com.github.kright.utils.DilatedExecutor
import com.typesafe.config.{Config, ConfigException, ConfigValueFactory, ConfigValueType}

import scala.collection.JavaConverters._

/**
  * Created by Igor Slobodskov on 02 May 2018
  */
object ConfigLoader {

  implicit class ConfigExt(val config: Config) extends AnyVal {

    def catchErrors[T](func: => T): T = {
      try {
        func
      } catch {
        case ex: ConfigException.Missing =>
          throw new ParsingError(s"${ex.getMessage} at line ${config.origin().lineNumber()} in $config")
        case ex: ConfigException.WrongType =>
          throw new ParsingError(s"${ex.getMessage} in $config")
      }
    }

    def as[T](implicit converter: ConfigConverter[T]): T = {
      catchErrors(converter.convert(config))
    }

    def getAs[T](path: String)(implicit converter: ConfigConverter[T]): T = config.getConfig(path).as[T]

    def getStrings(path: String): Seq[String] =
      if (config.hasPath(path)) {
        config.getValue(path).valueType() match {
          case ConfigValueType.STRING => Seq(config.getString(path))
          case ConfigValueType.LIST => config.getStringList(path).asScala
          case _ => throw new ParsingError(s"wrong type in '$path' at line ${config.origin().lineNumber()} in $config")
        }
      } else {
        List.empty
      }

    def getConfigs(path: String): Seq[Config] =
      if (config.hasPath(path)) {
        config.getConfigList(path).asScala
      } else {
        List.empty
      }


    def asLinked[T](implicit converter: DilatedConverter[T], gameRules: GameRules, dilatedExecutor: DilatedExecutor): T =
      catchErrors(converter.convert(config, gameRules, dilatedExecutor))

    def asLinked[T](path: String)(implicit converter: DilatedConverter[T], gameRules: GameRules, dilatedExecutor: DilatedExecutor): T =
      catchErrors(converter.convert(config.getConfig(path), gameRules, dilatedExecutor))

    def getOption[T](path: String)(implicit getOption: ConfigGetOption[T]): Option[T] =
      getOption.read(config, path)

    def getNamedEntries(path: String): Seq[Config] = {
      if (!config.hasPath(path))
        return List.empty

      val innerConf = config.getConfig(path)
      config.getKeys(path).map { name =>
        innerConf.getConfig(name).withValue("name", ConfigValueFactory.fromAnyRef(name))
      }
    }

    def getKeys(path: String): Seq[String] = config.getObject(path).keySet().asScala.toSeq
  }

  implicit class DoLateExt[T](val a: T) extends AnyVal {
    def doLate(func: => Unit)(implicit dilated: DilatedExecutor): Unit = {
      dilated.add { () => func }
    }
  }

  implicit val converterCellProduction: ConfigConverter[MutableCellProduction] = CellProduction
  implicit val converterBuildingEffectImpl: ConfigConverter[BuildingEffectImpl] = BuildingEffect
  implicit val converterTerrainTypeImpl: ConfigConverter[TerrainTypeImpl] = TerrainType

  implicit val converterResourceType: DilatedConverter[ResourceTypeImpl] = ResourceType
  implicit val converterTechnologyDescriptionImpl: DilatedConverter[TechnologyDescriptionImpl] = TechnologyDescription
  implicit val converterRequirementForCityProduction: DilatedConverter[RequirementForCityProduction] = RequirementForCityProduction
  implicit val converterCityBuildingType: DilatedConverter[CityBuildingTypeImpl] = CityBuildingType
  implicit val converterLandUpgradeType: DilatedConverter[LandUpgradeTypeImpl] = LandUpgradeType
  implicit val converterGameUnitType: DilatedConverter[GameUnitTypeImpl] = GameUnitType
  implicit val convertNationImpl: DilatedConverter[NationImpl] = Nation
  implicit val convertGameUnitActionType: DilatedConverter[GameUnitActionType] = GameUnitActionType


  implicit val getIntOption: ConfigGetOption[Int] = new ConfigGetOption[Int](_.getInt(_))
  implicit val getStringOption: ConfigGetOption[String] = new ConfigGetOption[String](_.getString(_))
  implicit val getBooleanOption: ConfigGetOption[Boolean] = new ConfigGetOption[Boolean](_.getBoolean(_))
  implicit val getConfigOption: ConfigGetOption[Config] = new ConfigGetOption[Config](_.getConfig(_))
}

class ParsingError(msg: String) extends RuntimeException(msg)


trait DilatedConverter[T] {
  def convert(implicit config: Config, gameRules: GameRules, dilatedExecutor: DilatedExecutor): T
}

class ConfigGetOption[T](func: (Config, String) => T) {
  def read(config: Config, path: String): Option[T] =
    if (config.hasPath(path)) Option(func(config, path)) else None
}

trait ConfigConverter[T] {
  def convert(config: Config): T
}

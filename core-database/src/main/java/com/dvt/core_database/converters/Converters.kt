/*
 * Copyright 2025 Gideon Rotich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dvt.core_database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dvt.core_network.model.City
import com.dvt.core_network.model.Clouds
import com.dvt.core_network.model.Main
import com.dvt.core_network.model.Rain
import com.dvt.core_network.model.Sys
import com.dvt.core_network.model.Weather
import com.dvt.core_network.model.WeatherItem
import com.dvt.core_network.model.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters (private val gson: Gson){
    @TypeConverter
    fun fromCity(str: City): String {
        return Gson().toJson(str)
    }

    @TypeConverter
    fun toCity(str: String): City {
        return Gson().fromJson(str, object : TypeToken<City>() {}.type)
    }

    @TypeConverter
    fun fromWeatherList(weatherList: List<WeatherItem>): String {
        return Gson().toJson(weatherList)
    }

    @TypeConverter
    fun toWeatherList(weatherListString: String): List<WeatherItem> {
        return Gson().fromJson(weatherListString, object : TypeToken<List<WeatherItem>>() {}.type)
    }
}
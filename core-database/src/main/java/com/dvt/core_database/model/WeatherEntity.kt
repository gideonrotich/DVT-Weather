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
package com.dvt.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvt.core_network.model.City
import com.dvt.core_network.model.Clouds
import com.dvt.core_network.model.Main
import com.dvt.core_network.model.Rain
import com.dvt.core_network.model.Sys
import com.dvt.core_network.model.Weather
import com.dvt.core_network.model.WeatherItem
import com.dvt.core_network.model.Wind

@Entity(tableName = "weather_table")

data class WeatherEntity (
    val city: City,
    @PrimaryKey
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Int
)
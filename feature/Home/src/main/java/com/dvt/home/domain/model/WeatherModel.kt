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
package com.dvt.home.domain.model

import com.dvt.core_network.model.City
import com.dvt.core_network.model.WeatherItem

data class WeatherModel(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<WeatherItem>?,
    val message: Int?
)

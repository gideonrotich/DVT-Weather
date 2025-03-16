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
package com.dvt.core_network

import com.dvt.core.Util.Constants.WEATHER_FORECAST_ENDPOINT
import com.dvt.core_network.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WEATHER_FORECAST_ENDPOINT)
    suspend fun getForeCast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = "6f73d60762645118c25d90ec6eb9ae7a"
    ): WeatherResponse

}

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
package com.dvt.home.data.repository

import com.dvt.core.Util.Resource
import com.dvt.core_database.dao.WeatherDao
import com.dvt.core_network.WeatherApi
import com.dvt.core_network.model.WeatherResponse
import com.dvt.home.data.mapper.toDomain
import com.dvt.home.data.mapper.toEntity
import com.dvt.home.domain.model.WeatherModel
import com.dvt.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override fun getForecast(lat: String, lon: String): Flow<Resource<WeatherModel>> = flow {
        emit(Resource.Loading())

        val getWeatherFromDb = weatherDao.getWeather()?.toDomain()
        emit(Resource.Loading(data = getWeatherFromDb))

        try {
            val apiResponse = weatherApi.getForeCast(lat, lon)
            weatherDao.deleteWeather()
            weatherDao.insertWeather(apiResponse.toEntity())
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getWeatherFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getWeatherFromDb
                )
            )
        }
        val weather = weatherDao.getWeather().toDomain()
        emit(Resource.Success(weather))
    }
}
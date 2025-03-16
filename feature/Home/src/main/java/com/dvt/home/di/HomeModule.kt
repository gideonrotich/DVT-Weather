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
package com.dvt.home.di


import com.dvt.core_database.dao.WeatherDao
import com.dvt.core_network.WeatherApi
import com.dvt.core_network.model.Weather
import com.dvt.home.data.repository.WeatherRepositoryImpl
import com.dvt.home.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi,weatherDao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi,weatherDao)
    }
}
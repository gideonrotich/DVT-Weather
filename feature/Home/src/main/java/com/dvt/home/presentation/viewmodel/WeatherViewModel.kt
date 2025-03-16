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
package com.dvt.home.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.ViewModel
import com.dvt.core.Util.Resource
import com.dvt.home.domain.usecase.GetWeatherUseCase
import com.dvt.home.presentation.State.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.dvt.core.Util.getDayOfWeek
import com.dvt.home.presentation.State.WeatherItemModel

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weather = mutableStateOf(WeatherState())
    val weather: State<WeatherState> = _weather

    private val _isPermissionGranted = mutableStateOf(true)
    val isPermissionGranted: State<Boolean> = _isPermissionGranted

    /**
     * Updates the location permission status.
     */
    fun updatePermissionStatus(granted: Boolean) {
        _isPermissionGranted.value = granted
        Log.d("Permission", "Permission status updated: $granted")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val filteredWeather: State<List<WeatherItemModel>> = derivedStateOf {
        _weather.value.weather?.list
            ?.groupBy { it.dt_txt ?: "".substring(0, 10) }
            ?.mapValues { (_, weatherEntries) -> weatherEntries.first() }
            ?.values
            ?.take(5)
            ?.map { weather ->
                WeatherItemModel(
                    dayOfWeek = getDayOfWeek(weather.dt_txt ?: ""),
                    temperature = weather.main?.temp?.toInt() ?: 0,
                    description = weather.weather?.first()?.description ?: ""
                )
            } ?: emptyList()
    }

    /**
     * Fetches the weather forecast based on the provided latitude and longitude.
     */
    fun getForecast(lat: String, lon: String) {
        Log.d("Weather", "Fetching forecast for lat: $lat, lon: $lon")

        getWeatherUseCase(lat, lon).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _weather.value = WeatherState(weather = result.data)
                    Log.d("Weather", "Weather data fetched successfully")
                }
                is Resource.Error -> {
                    _weather.value = WeatherState(error = result.message ?: "An unexpected error occurred")
                    Log.e("Weather", "Error fetching weather: ${result.message}")
                }
                is Resource.Loading -> {
                    _weather.value = WeatherState(isLoading = true)
                    Log.d("Weather", "Fetching weather data...")
                }
            }
        }.launchIn(viewModelScope)
    }
}
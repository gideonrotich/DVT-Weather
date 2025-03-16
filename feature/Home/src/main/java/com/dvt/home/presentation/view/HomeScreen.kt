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
package com.dvt.home.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.dvt.home.presentation.viewmodel.WeatherViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.dvt.core.Util.getCurrentLocation
import com.dvt.core.Util.getDayOfWeek
import com.dvt.home.R
import com.dvt.home.presentation.State.WeatherItemModel
import com.dvt.home.presentation.view.components.PermissionDeniedScreen
import com.dvt.home.presentation.view.components.WeatherForecastScreen
import com.dvt.home.presentation.view.components.getWeatherDrawable
import com.google.android.gms.location.LocationServices

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weatherState by viewModel.weather
    val filteredWeather by viewModel.filteredWeather
    val isPermissionGranted by viewModel.isPermissionGranted

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            fetchWeatherData(viewModel, fusedLocationClient)
        } else {
            viewModel.updatePermissionStatus(false)
            Log.e("Permission", "Location permission denied")
        }
    }

    LaunchedEffect(Unit) {
        val permissionStatus =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            fetchWeatherData(viewModel, fusedLocationClient)
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            weatherState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Blue
                )
            }

            !isPermissionGranted -> {
                PermissionDeniedScreen()
            }

            else -> {
                WeatherForecastScreen(filteredWeather)
            }
        }
    }
}

private fun fetchWeatherData(
    viewModel: WeatherViewModel,
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient
) {
    getCurrentLocation(fusedLocationClient) { lat, lon ->
        Log.d("Weather", "Fetching weather data for lat: $lat, lon: $lon")
        viewModel.getForecast(lat, lon)
    }
}
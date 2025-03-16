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
package com.dvt.home.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvt.core.Util.kelvinToCelsius
import com.dvt.home.R
import com.dvt.home.presentation.State.WeatherItemModel

@Composable
fun WeatherHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "5 Day Forecast",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.bold)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun WeatherForecastScreen(weatherList: List<WeatherItemModel>) {
    val backgroundPainter: Painter? = if (weatherList.isNotEmpty()) {
        painterResource(id = getBackgroundDrawable(weatherList.first().description))
    } else {
        null
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        backgroundPainter?.let { painter ->
            Image(
                painter = painterResource(R.drawable.forest),
                contentDescription = "Weather Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            WeatherHeader()

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(weatherList) { weather ->
                    WeatherItem(weather)

                }
            }
        }
    }
}

@Composable
fun WeatherItem(weather: WeatherItemModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 12.dp)
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = weather.dayOfWeek,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.bold)),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.sp,
                        color = Color.Black
                    )
                )


                Image(
                    painter = painterResource(getWeatherDrawable(description = weather.description)),
                    contentDescription = "Weather",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(top = 10.dp),
                    contentScale = ContentScale.Fit
                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val temp = kelvinToCelsius(weather.temperature.toDouble())

                Text(
                    text = "${temp}°",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.bold)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        lineHeight = 28.sp,
                        letterSpacing = 0.sp,
                        color = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
fun PermissionDeniedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.illustration),
            contentDescription = "Permission Denied Illustration",
            modifier = Modifier.size(250.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "Location permission is required to fetch weather data.",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.semibold)),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
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
package com.dvt.core.Util

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import com.google.android.gms.location.FusedLocationProviderClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun getCurrentLocation(fusedLocationClient: FusedLocationProviderClient, onLocationReceived: (String, String) -> Unit) {
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                onLocationReceived(it.latitude.toString(), it.longitude.toString())
            }
        }
    } catch (e: SecurityException) {
        Log.e("LocationError", "Location permission not granted")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(dateStr: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date = LocalDateTime.parse(dateStr, formatter).toLocalDate()
    return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun kelvinToCelsius(kelvin: Double): Int {
    return (kelvin - 273.15).toInt()
}


package com.example.weatherappjetpack.domain.location

import android.location.Location

interface LocationTracker  {
    suspend fun getCurrentLocation() : Location?
}
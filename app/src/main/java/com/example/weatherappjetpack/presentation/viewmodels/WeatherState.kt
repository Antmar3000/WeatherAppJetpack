package com.example.weatherappjetpack.presentation.viewmodels

import com.example.weatherappjetpack.domain.WeatherData

data class WeatherState (
    val weatherData: WeatherData? = null,
    val isLoading : Boolean = false,
    val error: String? = null
)
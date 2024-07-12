package com.example.weatherappjetpack.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappjetpack.domain.WeatherData
import com.example.weatherappjetpack.data.network.VolleyAPI
import com.example.weatherappjetpack.domain.location.LocationData
import com.example.weatherappjetpack.domain.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val api: VolleyAPI,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _daysList = mutableStateOf(listOf<WeatherData>())
    val daysList = _daysList

    private val _currentDay = mutableStateOf(getEmptyWeather())
    val currentDay = _currentDay

    private val _dialogState = mutableStateOf(false)
    val dialogState = _dialogState

    private val _locationState = mutableStateOf(getEmptyLocation())
    val locationState = _locationState


    private fun getEmptyWeather(): WeatherData {
        return WeatherData(
            "", "", "", "", "",
            "", "", "", "1", ""
        )
    }

    private fun getEmptyLocation(): LocationData {
        return LocationData(0.0, 0.0)
    }


    fun getLocation() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let {location ->

                locationState.value = LocationData(location.latitude, location.longitude)
            }
        }
    }


    fun getData(
        city: String,
        context: Context,
        daysList: MutableState<List<WeatherData>>,
        currentDay: MutableState<WeatherData>
    ) = viewModelScope.launch {
        api.getData(city, context, daysList, currentDay)
    }

    fun getWeatherByHours(hours: String): List<WeatherData> {
        return api.getWeatherByHours(hours)
    }


}



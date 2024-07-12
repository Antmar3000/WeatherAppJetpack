package com.example.weatherappjetpack.data.network

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappjetpack.domain.WeatherData
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class VolleyAPI @Inject constructor() {

    fun getData(
        city: String,
        context: Context,
        daysList: MutableState<List<WeatherData>>,
        currentDay: MutableState<WeatherData>
    ) {
        val url = RequestData.BASE_URL +
                RequestData.API_KEY +
                "&q=$city" +
                "&days=" +
                "7" +
                "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val list = getWeatherByDays(response)
                currentDay.value = list[0]
                daysList.value = list
            },
            {
                Log.d("mylog", "VolleyError $it")
            }
        )

        queue.add(stringRequest)
    }

    private fun getWeatherByDays(response: String): List<WeatherData> {
        if (response.isEmpty()) return listOf()
        else {
            val list = ArrayList<WeatherData>()
            val mainObject = JSONObject(response)
            val city = mainObject.getJSONObject("location").getString("name")
            val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

            for (i in 0 until days.length()) {
                val item = days[i] as JSONObject
                list.add(
                    WeatherData(
                        city,
                        item.getString("date"),
                        "",
                        item.getJSONObject("day").getJSONObject("condition").getString("text"),
                        item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                        item.getJSONObject("day").getString("maxtemp_c"),
                        item.getJSONObject("day").getString("mintemp_c"),
                        item.getJSONArray("hour").toString(),
                        item.getJSONObject("astro").getString("is_sun_up"),
                        item.getJSONObject("day").getString("avgtemp_c")
                    )
                )
            }

            list[0] = list[0].copy(
                time = mainObject.getJSONObject("current").getString("last_updated"),
                currentTemp = mainObject.getJSONObject("current").getString("temp_c"),
                isDay = mainObject.getJSONObject("current").getString("is_day"),
                feelsLike = mainObject.getJSONObject("current").getString("feelslike_c")
            )
            return list
        }

    }

    fun getWeatherByHours(hours: String): List<WeatherData> {
        if (hours.isEmpty()) return listOf()
        val hoursArray = JSONArray(hours)
        val list = ArrayList<WeatherData>()
        for (i in 0 until hoursArray.length()) {
            val item = hoursArray[i] as JSONObject
            list.add(
                WeatherData(
                    "",
                    item.getString("time"),
                    item.getString("temp_c"),
                    item.getJSONObject("condition").getString("text"),
                    item.getJSONObject("condition").getString("icon"),
                    "", "", "", "", ""
                )
            )
        }
        return list
    }


}
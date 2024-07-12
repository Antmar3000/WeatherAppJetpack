package com.example.weatherappjetpack.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappjetpack.R
import com.example.weatherappjetpack.domain.WeatherData
import com.example.weatherappjetpack.ui.theme.LightBlue
import com.example.weatherappjetpack.presentation.viewmodels.WeatherViewModel


@Composable
fun BackgroundImage(viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    fun getImageDayNight(currentDay: MutableState<WeatherData>): Int {
        return when (currentDay.value.isDay.toFloat().toInt()) {
            0 -> R.drawable.night_bg
            1 -> R.drawable.sunny_bg
            else -> R.drawable.sunny_bg
        }
    }
    Image(
        painter = painterResource(id = getImageDayNight(viewModel.currentDay)),
        contentDescription = "image_bg",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.8f),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun MainWeatherCard(
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onClickSync: () -> Unit,
    onClickSearch: () -> Unit
) {

    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = LightBlue
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = stringResource(id = R.string.now_title),
                        style = TextStyle(
                            fontSize = 15.sp
                        ),
                        color = Color.White
                    )

                    Box(
                        modifier = Modifier.background(Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = "https:${viewModel.currentDay.value.icon}",
                            contentDescription = "image_weather_condition",
                            modifier = Modifier.size(35.dp)
                        )
                    }


                }

                Text(
                    text = viewModel.currentDay.value.city, style = TextStyle(
                        fontSize = 24.sp, color = Color.White
                    )
                )

                Text(
                    text = viewModel.currentDay.value.currentTemp,
                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )

                Text(
                    text = viewModel.currentDay.value.condition,
                    style = TextStyle(fontSize = 12.sp),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onClickSearch.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "icon_search",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "${viewModel.currentDay.value.maxTemp}/${viewModel.currentDay.value.minTemp}",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )

                    Text(
                        text = "Feels like ${viewModel.currentDay.value.feelsLike}",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )

                    IconButton(onClick = { onClickSync.invoke() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "icon_sync",
                            tint = Color.White
                        )
                    }
                }

            }
        }



    }

}


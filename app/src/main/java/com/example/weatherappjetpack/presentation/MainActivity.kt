package com.example.weatherappjetpack.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import com.example.weatherappjetpack.data.location.RequestLocationPermission
import com.example.weatherappjetpack.ui.theme.WeatherAppJetpackTheme
import com.example.weatherappjetpack.presentation.ui.BackgroundImage
import com.example.weatherappjetpack.presentation.ui.MainWeatherCard
import com.example.weatherappjetpack.presentation.ui.SearchDialog
import com.example.weatherappjetpack.presentation.ui.TabLayout
import com.example.weatherappjetpack.presentation.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData(
            "Saint-Petersburg",
            this,
            viewModel.daysList,
            viewModel.currentDay
        )

        setContent {
            WeatherAppJetpackTheme {

                RequestLocationPermission(
                    onPermissionGranted = { },
                    onPermissionDenied = { },
                    onPermissionsRevoked = { })


                if (viewModel.dialogState.value) {
                    SearchDialog(onSubmit = {
                        viewModel.getData(it, this, viewModel.daysList, viewModel.currentDay)
                    })
                }

                BackgroundImage()

                Column {
                    MainWeatherCard(
                        onClickSync = {
                            viewModel.getLocation()
                            val cityLocation =
                                "${viewModel.locationState.value.latitude}, ${viewModel.locationState.value.longitude}"
                            viewModel.getData(
                                cityLocation,
                                this@MainActivity,
                                viewModel.daysList,
                                viewModel.currentDay
                            )
                        },
                        onClickSearch = {
                            viewModel.dialogState.value = true
                        })


                    TabLayout()
                }

            }

        }
    }

}




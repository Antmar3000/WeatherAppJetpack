package com.example.weatherappjetpack.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.weatherappjetpack.presentation.viewmodels.WeatherViewModel
import com.example.weatherappjetpack.ui.theme.LightBlueNoTransparent

@Composable
fun SearchDialog(
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onSubmit: (String) -> Unit
) {


    val textState = remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
        viewModel.dialogState.value = false
    }, confirmButton = {
        TextButton(onClick = {
            onSubmit(textState.value)
            viewModel.dialogState.value = false
        }) {
            Text(text = "OK",
                color = Color.White)
        }
    },
        dismissButton = {
            TextButton(onClick = { viewModel.dialogState.value = false }) {
                Text(text = "Cancel",
                    color = Color.White)
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Input City name")
                TextField(value = textState.value, onValueChange = {
                    textState.value = it
                })
            }

        },
        containerColor = LightBlueNoTransparent
    )


}
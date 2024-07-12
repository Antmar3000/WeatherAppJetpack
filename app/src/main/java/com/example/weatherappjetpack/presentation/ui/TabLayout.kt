package com.example.weatherappjetpack.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherappjetpack.R
import com.example.weatherappjetpack.presentation.viewmodels.WeatherViewModel
import com.example.weatherappjetpack.ui.theme.LightBlue
import kotlinx.coroutines.launch

@Composable
fun TabLayout(viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val tabList =
        listOf(stringResource(id = R.string.hours_pager), stringResource(id = R.string.days_pager))
    val pagerState = rememberPagerState(pageCount = { tabList.size })
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex, containerColor = LightBlue, contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(
                        text = text,
                    )
                })
            }
        }

        HorizontalPager(
            state = pagerState, modifier = Modifier.weight(1f)
        ) { page ->
            val list = when (page) {
                0 -> viewModel.getWeatherByHours(viewModel.currentDay.value.hours)
                1 -> viewModel.daysList.value
                else -> viewModel.daysList.value
            }
            MainList(list = list)
        }
    }
}
package io.github.taetae98coding.diary.feature.more.holiday

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.PrimitiveCalendarItemKey
import io.github.taetae98coding.diary.compose.calendar.month.Month
import io.github.taetae98coding.diary.compose.calendar.month.rememberMonthState
import io.github.taetae98coding.diary.compose.core.effect.PlatformRefreshLifecycleEffect
import io.github.taetae98coding.diary.compose.core.icon.ChevronLeftIcon
import io.github.taetae98coding.diary.compose.core.icon.ChevronRightIcon
import io.github.taetae98coding.diary.compose.core.icon.MinusIcon
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.icon.PlusIcon
import io.github.taetae98coding.diary.compose.core.icon.SettingsIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.number
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GoldHolidayScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoldHolidayViewModel = koinViewModel(),
) {
    val state = rememberGoldHolidayScreenState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val fetch = { viewModel.fetch(state.year, state.annualLeave) }

    GoldHolidayScreen(
        state = state,
        uiStateProvider = { uiState },
        onNavigateUp = dropUnlessResumed { onNavigateUp() },
        onFetch = fetch,
        modifier = modifier,
    )

    FetchEffect(
        state = state,
        fetch = fetch,
    )
}

@Composable
private fun GoldHolidayScreen(
    state: GoldHolidayState,
    uiStateProvider: () -> GoldHolidayUiState,
    onNavigateUp: () -> Unit,
    onFetch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                state = state,
                onNavigateUp = onNavigateUp,
            )
        },
    ) {
        AnimatedContent(
            targetState = uiStateProvider(),
            modifier = Modifier.fillMaxSize()
                .padding(it),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            contentKey = { uiState ->
                when (uiState) {
                    is GoldHolidayUiState.FetchProgress -> "FetchProgress"
                    is GoldHolidayUiState.Result -> "Result"
                    is GoldHolidayUiState.Error -> "Error"
                }
            },
        ) { uiState ->
            when (uiState) {
                is GoldHolidayUiState.FetchProgress -> {
                    LoadingBox(
                        uiState = uiState,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is GoldHolidayUiState.Result -> {
                    if (uiState.yearMonthUiState.isEmpty()) {
                        EmptyBox(modifier = Modifier.fillMaxSize())
                    } else {
                        ResultPager(
                            uiState = uiState,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }

                is GoldHolidayUiState.Error -> {
                    RetryBox(
                        onRetry = onFetch,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

    SettingDialogHost(state)
}

@Composable
private fun TopBar(
    state: GoldHolidayState,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { coroutineScope.launch { state.prevMonth() } }) {
                    ChevronLeftIcon()
                }
                HorizontalPager(
                    state = state.pagerState,
                    modifier = Modifier.width(120.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "${state.year}년")
                    }
                }
                IconButton(onClick = { coroutineScope.launch { state.nextMonth() } }) {
                    ChevronRightIcon()
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
        actions = {
            IconButton(onClick = state::showSetting) {
                SettingsIcon()
            }
        },
    )
}

@Composable
private fun LoadingBox(
    uiState: GoldHolidayUiState.FetchProgress,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ContainedLoadingIndicator()
            Text(text = "공휴일 정보를 받아오고 있습니다")
            Text(text = "${uiState.percentage * 100}%")
        }
    }
}

@Composable
private fun EmptyBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "공휴일이 제공되지 않습니다")
    }
}

@Composable
private fun ResultPager(
    uiState: GoldHolidayUiState.Result,
    modifier: Modifier = Modifier,
) {
    val color = DiaryTheme.colorScheme.primary

    HorizontalPager(
        state = remember(uiState) { PagerState { uiState.yearMonthUiState.size } },
        modifier = modifier,
    ) { page ->
        val yearMonthUiState = uiState.yearMonthUiState[page]

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${yearMonthUiState.yearMonth.month.number}월 - 황금연휴 ${yearMonthUiState.goldHoliday}일")
            Month(
                state = rememberMonthState(yearMonthUiState.yearMonth),
                colorTextProvider = {
                    yearMonthUiState.annualLeave.map {
                        CalendarTextItemUiState.ColorText(
                            key = PrimitiveCalendarItemKey(it.toString()),
                            text = "휴가",
                            dateRange = it,
                            color = color.toArgb(),
                        )
                    }
                },
                holidayProvider = { yearMonthUiState.holiday },
            )
        }
    }
}

@Composable
private fun RetryBox(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "네트워크 상태를 확인하세요")
            Button(onClick = onRetry) {
                Text(text = "재시도")
            }
        }
    }
}

@Composable
private fun SettingDialogHost(
    state: GoldHolidayState,
) {
    if (state.isSettingDialogVisible) {
        BasicAlertDialog(onDismissRequest = state::hideSetting) {
            Card {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "휴가",
                        modifier = Modifier.padding(8.dp),
                        style = DiaryTheme.typography.titleMediumEmphasized,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = state::minusAnnualLeave) {
                            MinusIcon()
                        }

                        Text(text = state.annualLeave.toString())

                        IconButton(onClick = state::plusAnnualLeave) {
                            PlusIcon()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FetchEffect(
    state: GoldHolidayState,
    fetch: () -> Unit,
) {
    PlatformRefreshLifecycleEffect(state.year, state.annualLeave) {
        fetch()
    }
}

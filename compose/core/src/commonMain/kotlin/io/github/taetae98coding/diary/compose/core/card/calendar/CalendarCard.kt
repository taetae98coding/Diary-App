package io.github.taetae98coding.diary.compose.core.card.calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

@Composable
public fun CalendarCard(
    state: CalendarCardState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        TitleRow(
            state = state,
            modifier = Modifier.fillMaxWidth(),
        )
        AnimatedVisibility(
            state.hasDate,
        ) {
            DateRow(state = state)
        }
    }
}

@Composable
private fun TitleRow(
    state: CalendarCardState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .toggleable(
                value = state.hasDate,
                onValueChange = { hasDate ->
                    if (hasDate) {
                        state.setDate()
                    } else {
                        state.clearDate()
                    }
                },
            )
            .minimumInteractiveComponentSize()
            .padding(DiaryTheme.dimens.diaryPaddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "캘린더")
        Switch(
            checked = state.hasDate,
            onCheckedChange = null,
        )
    }
}

@Composable
private fun DateRow(
    state: CalendarCardState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(DiaryTheme.dimens.diaryPaddingValues),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LocalDateButton(
            state.start,
            onClick = state::showStartDatePicker,
            modifier = Modifier.weight(1F),
        )

        Text(text = "~")

        LocalDateButton(
            state.endInclusive,
            onClick = state::showEndInclusiveDatePicker,
            modifier = Modifier.weight(1F),
        )
    }
}

@Composable
private fun LocalDateButton(
    localDate: LocalDate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedContent(
                targetState = localDate.year,
                transitionSpec = transitionSpec(),
            ) {
                Text(
                    text = "${it}년",
                    style = DiaryTheme.typography.labelSmall,
                )
            }
            AnimatedContent(
                targetState = localDate,
                transitionSpec = transitionSpec(),
            ) {
                Text(
                    text = "${it.month.number}월 ${it.day}일",
                    style = DiaryTheme.typography.labelLarge,
                )
            }
        }
    }
}

private fun <T : Comparable<T>> transitionSpec(): AnimatedContentTransitionScope<T>.() -> ContentTransform {
    return {
        if (targetState > initialState) {
            val inAnimation = slideInVertically { height -> height } + fadeIn()
            val outAnimation = slideOutVertically { height -> -height } + fadeOut()

            inAnimation togetherWith outAnimation
        } else {
            val inAnimation = slideInVertically { height -> -height } + fadeIn()
            val outAnimation = slideOutVertically { height -> height } + fadeOut()

            inAnimation togetherWith outAnimation
        }.using(
            SizeTransform(clip = false),
        )
    }
}

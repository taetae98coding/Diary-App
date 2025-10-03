package io.github.taetae98coding.diary.presenter.memo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.github.taetae98coding.diary.compose.core.card.calendar.CalendarCardState
import io.github.taetae98coding.diary.compose.core.card.calendar.rememberCalendarCardState
import io.github.taetae98coding.diary.compose.core.card.color.ColorCardState
import io.github.taetae98coding.diary.compose.core.card.color.rememberColorCardState
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCardState
import io.github.taetae98coding.diary.compose.core.card.description.rememberDescriptionCardState
import io.github.taetae98coding.diary.compose.core.card.title.TitleCardState
import io.github.taetae98coding.diary.compose.core.card.title.rememberTitleCardState
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.library.compose.color.randomColor
import kotlinx.datetime.LocalDateRange

internal data class MemoDetailState(
    val titleCardState: TitleCardState,
    val descriptionCardState: DescriptionCardState,
    val calendarCardState: CalendarCardState,
    val colorCardState: ColorCardState,
) {
    val detail: MemoDetail
        get() = MemoDetail(
            title = titleCardState.textFieldState.text.toString(),
            description = descriptionCardState.textFieldState.text.toString(),
            dateRange = calendarCardState.dateRange.takeIf { calendarCardState.hasDate },
            color = colorCardState.color.toArgb(),
        )
}

@Composable
internal fun rememberMemoDetailState(
    initialDateRange: LocalDateRange? = null,
): MemoDetailState {
    val titleCardState = rememberTitleCardState()
    val descriptionCardState = rememberDescriptionCardState()
    val calendarCardState = rememberCalendarCardState(
        inputs = arrayOf(initialDateRange),
        initialDateRange = initialDateRange,
    )
    val colorCardState = rememberColorCardState()

    return remember(
        titleCardState,
        descriptionCardState,
        calendarCardState,
        colorCardState,
    ) {
        MemoDetailState(
            titleCardState = titleCardState,
            descriptionCardState = descriptionCardState,
            calendarCardState = calendarCardState,
            colorCardState = colorCardState,
        )
    }
}

@Composable
internal fun rememberMemoDetailState(
    detailProvider: () -> MemoDetail?,
): MemoDetailState {
    val detail = detailProvider()
    val titleCardState = rememberTitleCardState(
        inputs = arrayOf(detail),
        initialText = detail?.title.orEmpty(),
    )
    val descriptionCardState = rememberDescriptionCardState(
        inputs = arrayOf(detail),
        initialText = detail?.description.orEmpty(),
    )
    val calendarCardState = rememberCalendarCardState(
        inputs = arrayOf(detail),
        initialDateRange = detail?.dateRange,
    )
    val colorCardState = rememberColorCardState(
        inputs = arrayOf(detail),
        initialColor = detail?.color?.let { Color(it) } ?: randomColor(),
    )

    return remember(
        titleCardState,
        descriptionCardState,
        calendarCardState,
        colorCardState,
    ) {
        MemoDetailState(
            titleCardState = titleCardState,
            descriptionCardState = descriptionCardState,
            calendarCardState = calendarCardState,
            colorCardState = colorCardState,
        )
    }
}

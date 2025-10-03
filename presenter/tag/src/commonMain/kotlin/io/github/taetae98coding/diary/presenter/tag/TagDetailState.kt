package io.github.taetae98coding.diary.presenter.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.github.taetae98coding.diary.compose.core.card.color.ColorCardState
import io.github.taetae98coding.diary.compose.core.card.color.rememberColorCardState
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCardState
import io.github.taetae98coding.diary.compose.core.card.description.rememberDescriptionCardState
import io.github.taetae98coding.diary.compose.core.card.title.TitleCardState
import io.github.taetae98coding.diary.compose.core.card.title.rememberTitleCardState
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.library.compose.color.randomColor

internal data class TagDetailState(
    val titleCardState: TitleCardState,
    val descriptionCardState: DescriptionCardState,
    val colorCardState: ColorCardState,
) {
    val detail: TagDetail
        get() = TagDetail(
            title = titleCardState.textFieldState.text.toString(),
            description = descriptionCardState.textFieldState.text.toString(),
            color = colorCardState.color.toArgb(),
        )
}

@Composable
internal fun rememberTagDetailState(): TagDetailState {
    val titleCardState = rememberTitleCardState()
    val descriptionCardState = rememberDescriptionCardState()
    val colorCardState = rememberColorCardState()

    return remember(
        titleCardState,
        descriptionCardState,
        colorCardState,
    ) {
        TagDetailState(
            titleCardState = titleCardState,
            descriptionCardState = descriptionCardState,
            colorCardState = colorCardState,
        )
    }
}

@Composable
internal fun rememberTagDetailState(
    detailProvider: () -> TagDetail?,
): TagDetailState {
    val detail = detailProvider()
    val titleCardState = rememberTitleCardState(
        inputs = arrayOf(detail),
        initialText = detail?.title.orEmpty(),
    )
    val descriptionCardState = rememberDescriptionCardState(
        inputs = arrayOf(detail),
        initialText = detail?.description.orEmpty(),
    )
    val colorCardState = rememberColorCardState(
        inputs = arrayOf(detail),
        initialColor = detail?.color?.let { Color(it) } ?: randomColor(),
    )

    return remember(
        titleCardState,
        descriptionCardState,
        colorCardState,
    ) {
        TagDetailState(
            titleCardState = titleCardState,
            descriptionCardState = descriptionCardState,
            colorCardState = colorCardState,
        )
    }
}

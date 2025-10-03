package io.github.taetae98coding.diary.feature.buddy.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCardState
import io.github.taetae98coding.diary.compose.core.card.description.rememberDescriptionCardState
import io.github.taetae98coding.diary.compose.core.card.title.TitleCardState
import io.github.taetae98coding.diary.compose.core.card.title.rememberTitleCardState
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail

internal data class BuddyGroupDetailState(
    val titleCardState: TitleCardState,
    val descriptionCardState: DescriptionCardState,
) {
    val detail: BuddyGroupDetail
        get() = BuddyGroupDetail(
            title = titleCardState.textFieldState.text.toString(),
            description = descriptionCardState.textFieldState.text.toString(),
        )
}

@Composable
internal fun rememberBuddyGroupDetailState(): BuddyGroupDetailState {
    val titleCardState = rememberTitleCardState()
    val descriptionCardState = rememberDescriptionCardState()

    return remember(
        titleCardState,
        descriptionCardState,
    ) {
        BuddyGroupDetailState(
            titleCardState = titleCardState,
            descriptionCardState = descriptionCardState,
        )
    }
}

@Composable
internal fun rememberBuddyGroupDetailState(
    detailProvider: () -> BuddyGroupDetail?,
): BuddyGroupDetailState {
    val detail = detailProvider()
    val titleCardState = rememberTitleCardState(
        inputs = arrayOf(detail?.title),
        initialText = detail?.title.orEmpty(),
    )
    val descriptionCardState = rememberDescriptionCardState(
        inputs = arrayOf(detail?.description),
        initialText = detail?.description.orEmpty(),
    )

    return remember(
        titleCardState,
        descriptionCardState,
    ) {
        BuddyGroupDetailState(
            titleCardState = titleCardState,
            descriptionCardState = descriptionCardState,
        )
    }
}

package io.github.taetae98coding.diary.presenter.memo.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.compose.core.icon.ChevronRightIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag
import io.github.taetae98coding.diary.library.compose.color.wcagAAAContentColor
import kotlin.uuid.Uuid

@Composable
internal fun MemoTagCard(
    pagingItems: LazyPagingItems<PrimaryMemoTag>,
    onTagTitle: () -> Unit,
    onTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    TagCard(
        pagingItems = pagingItems,
        title = {
            Row(
                modifier = Modifier.clickable(onClick = onTagTitle)
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .padding(horizontal = DiaryTheme.dimens.diaryHorizontalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "태그")
                ChevronRightIcon()
            }
        },
        tag = { uiState ->
            MemoTagChip(
                uiState = uiState,
                onClick = { uiState?.tag?.id?.let(onTag) },

            )
        },
        modifier = modifier,
    )
}

@Composable
internal fun MemoTagChip(
    uiState: PrimaryMemoTag?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TagChip(
        isSelected = uiState?.isPrimary ?: false,
        title = uiState?.tag?.detail?.title.orEmpty(),
        onClick = onClick,
        modifier = if (uiState == null) {
            modifier.width(80.dp)
        } else {
            modifier
        },
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = uiState?.tag?.detail?.color?.let { Color(it) } ?: Color.Unspecified,
            selectedLabelColor = uiState?.tag?.detail?.color?.let { Color(it).wcagAAAContentColor() } ?: Color.Unspecified,
            selectedLeadingIconColor = uiState?.tag?.detail?.color?.let { Color(it).wcagAAAContentColor() } ?: Color.Unspecified,
        ),
    )
}

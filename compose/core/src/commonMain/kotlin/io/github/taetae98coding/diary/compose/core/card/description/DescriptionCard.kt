package io.github.taetae98coding.diary.compose.core.card.description

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.mikepenz.markdown.m3.Markdown
import io.github.taetae98coding.diary.compose.core.icon.MarkdownIcon
import io.github.taetae98coding.diary.compose.core.icon.TextFieldsIcon
import io.github.taetae98coding.diary.compose.core.text.ClearTextField
import kotlinx.coroutines.launch

@Composable
public fun DescriptionCard(
    state: DescriptionCardState,
    modifier: Modifier = Modifier,
    previousFocusProperty: FocusRequester = FocusRequester.Default,
) {
    Card(modifier = modifier) {
        DescriptionTabRow(state = state)
        DescriptionPager(
            state = state,
            previousFocusProperty = previousFocusProperty,
        )
    }
}

@Composable
private fun DescriptionTabRow(
    state: DescriptionCardState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    SecondaryTabRow(
        selectedTabIndex = state.pagerState.currentPage,
        modifier = modifier,
    ) {
        repeat(2) { page ->
            Tab(
                selected = state.pagerState.currentPage == page,
                onClick = { coroutineScope.launch { state.pagerState.animateScrollToPage(page) } },
                icon = {
                    when (page) {
                        0 -> TextFieldsIcon()
                        1 -> MarkdownIcon()
                    }
                },
            )
        }
    }
}

@Composable
private fun DescriptionPager(
    state: DescriptionCardState,
    modifier: Modifier = Modifier,
    previousFocusProperty: FocusRequester = FocusRequester.Default,
) {
    val density = LocalDensity.current
    val contentHeight = remember { mutableIntStateOf(0) }

    HorizontalPager(
        state = state.pagerState,
        modifier = modifier,
        beyondViewportPageCount = 1,
    ) { page ->
        if (page == 0) {
            ClearTextField(
                state = state.textFieldState,
                modifier = Modifier.fillMaxSize()
                    .onSizeChanged { contentHeight.value = it.height }
                    .focusRequester(state.focusRequester)
                    .focusProperties { previous = previousFocusProperty },
                label = { Text(text = "설명") },
                keyboardOptions = KeyboardOptions.Default,
                onKeyboardAction = null,
                lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 10),
            )
        } else {
            Markdown(
                content = state.textFieldState.text.toString(),
                modifier = Modifier.fillMaxWidth()
                    .height(with(density) { contentHeight.value.toDp() })
                    .verticalScroll(rememberScrollState())
                    .padding(TextFieldDefaults.contentPaddingWithLabel()),
            )
        }
    }
}

package io.github.taetae98coding.diary.feature.calendar.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.domain.calendar.usecase.AddCalendarMemoTagFilterUseCase
import io.github.taetae98coding.diary.domain.calendar.usecase.PageCalendarMemoTagFilterUseCase
import io.github.taetae98coding.diary.domain.calendar.usecase.RemoveCalendarMemoTagFilterUseCase
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CalendarMemoTagFilterViewModel(
    pageCalendarMemoTagFilterUseCase: PageCalendarMemoTagFilterUseCase,
    private val addCalendarMemoTagFilterUseCase: AddCalendarMemoTagFilterUseCase,
    private val removeCalendarMemoTagFilterUseCase: RemoveCalendarMemoTagFilterUseCase,
) : ViewModel() {
    val pagingData = pageCalendarMemoTagFilterUseCase().mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(viewModelScope)

    fun add(tagId: Uuid) {
        viewModelScope.launch {
            addCalendarMemoTagFilterUseCase(tagId)
        }
    }

    fun remove(tagId: Uuid) {
        viewModelScope.launch {
            removeCalendarMemoTagFilterUseCase(tagId)
        }
    }
}

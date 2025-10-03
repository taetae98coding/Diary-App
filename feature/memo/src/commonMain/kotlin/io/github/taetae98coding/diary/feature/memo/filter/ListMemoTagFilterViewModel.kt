package io.github.taetae98coding.diary.feature.memo.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.domain.memo.usecase.AddListMemoTagFilterUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.PageListMemoTagFilterUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RemoveListMemoTagFilterUseCase
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ListMemoTagFilterViewModel(
    pageListMemoTagFilterUseCase: PageListMemoTagFilterUseCase,
    private val addListMemoTagFilterUseCase: AddListMemoTagFilterUseCase,
    private val removeListMemoTagFilterUseCase: RemoveListMemoTagFilterUseCase,
) : ViewModel() {
    val pagingData = pageListMemoTagFilterUseCase().mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(viewModelScope)

    fun add(tagId: Uuid) {
        viewModelScope.launch {
            addListMemoTagFilterUseCase(tagId)
        }
    }

    fun remove(tagId: Uuid) {
        viewModelScope.launch {
            removeListMemoTagFilterUseCase(tagId)
        }
    }
}

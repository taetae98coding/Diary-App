package io.github.taetae98coding.diary.presenter.memo.filter

import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

public class MemoListFilterStateHolderTemplate(
    private val strategy: MemoListFilterStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : MemoListFilterStateHolder,
    AutoCloseable {
    override val pagingData: Flow<PagingData<TagFilter>> = strategy.pageTagFilter()
        .mapLatest { result ->
            result.fold(
                onSuccess = { it },
                onFailure = {
                    Logger.log("[MemoListFilterStateHolderTemplate] 태그 필터 페이지 로드 실패 : $it")
                    PagingData.loading()
                },
            )
        }.cachedIn(coroutineScope)

    override fun addTagFilter(tagId: Uuid) {
        coroutineScope.launch {
            strategy.addTagFilter(tagId)
        }
    }

    override fun removeTagFilter(tagId: Uuid) {
        coroutineScope.launch {
            strategy.removeTagFilter(tagId)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

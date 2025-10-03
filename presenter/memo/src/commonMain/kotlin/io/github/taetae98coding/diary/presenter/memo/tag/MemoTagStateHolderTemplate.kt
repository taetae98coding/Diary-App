package io.github.taetae98coding.diary.presenter.memo.tag

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.library.paging.common.filterPagingLatest
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

public class MemoTagStateHolderTemplate(
    private val strategy: MemoTagStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : MemoTagStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false
    private val memo = strategy.getMemo()
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )
    override val selectMemoTagPagingData: Flow<PagingData<SelectMemoTag>> = strategy.pageSelectMemoTag()
        .mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(coroutineScope)

    override val primaryMemoTagPagingData: Flow<PagingData<PrimaryMemoTag>> = selectMemoTagPagingData.filterPagingLatest { it.isSelected }
        .cachedIn(coroutineScope)
        .combine(memo) { pagingData, memo ->
            pagingData.map {
                PrimaryMemoTag(
                    isPrimary = it.tag.id == memo.getOrNull()?.primaryTag,
                    tag = it.tag,
                )
            }
        }
        .cachedIn(coroutineScope)

    private val _memoTagEffect = MutableStateFlow<MemoTagEffect>(MemoTagEffect.None)
    override val memoTagEffect: StateFlow<MemoTagEffect> = _memoTagEffect.asStateFlow()

    override fun fetchMemoTag() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure { throwable ->
                    Logger.log("[MemoTagStateHolderTemplate] 메모 태그 패치 실패 : $throwable")
                    _memoTagEffect.emit(MemoTagEffect.FetchError)
                }
            isInFetchProgress = false
        }
    }

    override fun selectPrimaryTag(tagId: Uuid) {
        coroutineScope.launch {
            strategy.selectPrimaryTag(tagId)
                .onFailure {
                    Logger.log("[MemoTagStateHolderTemplate] 기본 태그 선택 실패 : $it")
                    _memoTagEffect.emit(MemoTagEffect.UnknownError)
                }
        }
    }

    override fun removePrimaryTag() {
        coroutineScope.launch {
            strategy.removePrimaryTag()
                .onFailure {
                    Logger.log("[MemoTagStateHolderTemplate] 기본 태그 제거 실패 : $it")
                    _memoTagEffect.emit(MemoTagEffect.UnknownError)
                }
        }
    }

    override fun selectMemoTag(tagId: Uuid) {
        coroutineScope.launch {
            strategy.selectMemoTag(tagId)
                .onFailure {
                    Logger.log("[MemoTagStateHolderTemplate] 메모 태그 선택 실패 : $it")
                    _memoTagEffect.emit(MemoTagEffect.UnknownError)
                }
        }
    }

    override fun unselectMemoTag(tagId: Uuid) {
        coroutineScope.launch {
            strategy.unselectMemoTag(tagId)
                .onFailure {
                    Logger.log("[MemoTagStateHolderTemplate] 메모 태그 선택 해제 실패 : $it")
                    _memoTagEffect.emit(MemoTagEffect.UnknownError)
                }
        }
    }

    override fun clearMemoTagEffect() {
        coroutineScope.launch {
            _memoTagEffect.emit(MemoTagEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

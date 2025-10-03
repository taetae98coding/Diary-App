package io.github.taetae98coding.diary.presenter.memo.add

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.core.navigation.parameter.PrimaryTag
import io.github.taetae98coding.diary.domain.memo.exception.MemoTitleBlankException
import io.github.taetae98coding.diary.library.paging.common.loading
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagEffect
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public class MemoAddStateHolderTemplate(
    primaryTag: PrimaryTag,
    private val strategy: MemoAddStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : MemoAddStateHolder,
    AutoCloseable {
    private var isInMemoTagFetchProgress = false

    private val primaryTagId = MutableStateFlow(primaryTag.value)
    private val memoTagIds = MutableStateFlow(setOfNotNull(primaryTag.value))
    private val tagPagingData = strategy.pageTag()
        .mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(coroutineScope)

    override val primaryMemoTagPagingData: Flow<PagingData<PrimaryMemoTag>> = combine(primaryTagId, memoTagIds, tagPagingData) { primaryTagId, memoTagIds, pagingData ->
        pagingData.filter { it.id in memoTagIds }
            .map {
                PrimaryMemoTag(
                    isPrimary = primaryTagId == it.id,
                    tag = it,
                )
            }
    }.cachedIn(coroutineScope)

    override val selectMemoTagPagingData: Flow<PagingData<SelectMemoTag>> = combine(memoTagIds, tagPagingData) { memoTagIds, pagingData ->
        pagingData.map {
            SelectMemoTag(
                isSelected = it.id in memoTagIds,
                tag = it,
            )
        }
    }.cachedIn(coroutineScope)

    private val _floatingUiState = MutableStateFlow(MemoAddFloatingUiState())
    override val floatingUiState: StateFlow<MemoAddFloatingUiState> = _floatingUiState.asStateFlow()

    private val _memoAddEffect = MutableStateFlow<MemoAddEffect>(MemoAddEffect.None)
    override val memoAddEffect: StateFlow<MemoAddEffect> = _memoAddEffect.asStateFlow()

    private val _memoTagEffect = MutableStateFlow<MemoTagEffect>(MemoTagEffect.None)
    override val memoTagEffect: StateFlow<MemoTagEffect> = _memoTagEffect.asStateFlow()

    override fun fetchMemoTag() {
        if (isInMemoTagFetchProgress) return

        coroutineScope.launch {
            isInMemoTagFetchProgress = true
            strategy.fetchMemoTag()
                .onFailure { throwable ->
                    Logger.log("[MemoAddStateHolderTemplate] 메모 태그 패치 실패 : $throwable")
                    _memoTagEffect.emit(MemoTagEffect.FetchError)
                }
            isInMemoTagFetchProgress = false
        }
    }

    override fun add(detail: MemoDetail) {
        if (floatingUiState.value.isInProgress) return

        coroutineScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            strategy.addMemo(detail, primaryTagId.value, memoTagIds.value)
                .onSuccess { _memoAddEffect.emit(MemoAddEffect.AddSuccess) }
                .onFailure { throwable ->
                    Logger.log("[MemoAddStateHolderTemplate] 메모 추가 실패 : $throwable")
                    when (throwable) {
                        is MemoTitleBlankException -> _memoAddEffect.emit(MemoAddEffect.TitleBlank)
                        else -> _memoAddEffect.emit(MemoAddEffect.UnknownError)
                    }
                }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    override fun selectPrimaryTag(tagId: Uuid) {
        coroutineScope.launch {
            primaryTagId.emit(tagId)
        }
    }

    override fun removePrimaryTag() {
        coroutineScope.launch {
            primaryTagId.emit(null)
        }
    }

    override fun selectMemoTag(tagId: Uuid) {
        memoTagIds.update { it + tagId }
    }

    override fun unselectMemoTag(tagId: Uuid) {
        memoTagIds.update { it - tagId }
    }

    override fun clearMemoAddEffect() {
        coroutineScope.launch { _memoAddEffect.emit(MemoAddEffect.None) }
    }

    override fun clearMemoTagEffect(): Unit = Unit

    override fun close() {
        coroutineScope.cancel()
    }
}

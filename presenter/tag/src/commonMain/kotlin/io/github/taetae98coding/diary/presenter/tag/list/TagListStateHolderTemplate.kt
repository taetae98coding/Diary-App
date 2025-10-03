package io.github.taetae98coding.diary.presenter.tag.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

public class TagListStateHolderTemplate(
    private val strategy: TagListStrategy,
    private val coroutineScope: CoroutineScope = MainScope(),
) : TagListStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    override val pagingData: Flow<PagingData<Tag>> = strategy.pageTag().mapLatest { result ->
        result.fold(
            onSuccess = { it },
            onFailure = { PagingData.loading() },
        )
    }.cachedIn(coroutineScope)

    private val _effect = MutableStateFlow<TagListEffect>(TagListEffect.None)
    override val effect: StateFlow<TagListEffect> = _effect.asStateFlow()

    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure { _effect.emit(TagListEffect.FetchError) }
            isInFetchProgress = false
        }
    }

    override fun finish(tagId: Uuid) {
        coroutineScope.launch {
            strategy.finishTag(tagId)
                .onSuccess { _effect.emit(TagListEffect.FinishSuccess(tagId)) }
                .onFailure { _effect.emit(TagListEffect.UnknownError) }
        }
    }

    override fun delete(tagId: Uuid) {
        coroutineScope.launch {
            strategy.deleteTag(tagId)
                .onSuccess { _effect.emit(TagListEffect.DeleteSuccess(tagId)) }
                .onFailure { _effect.emit(TagListEffect.UnknownError) }
        }
    }

    override fun restart(tagId: Uuid) {
        coroutineScope.launch {
            strategy.restartTag(tagId)
                .onFailure { _effect.emit(TagListEffect.UnknownError) }
        }
    }

    override fun restore(tagId: Uuid) {
        coroutineScope.launch {
            strategy.restoreTag(tagId)
                .onFailure { _effect.emit(TagListEffect.UnknownError) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(TagListEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

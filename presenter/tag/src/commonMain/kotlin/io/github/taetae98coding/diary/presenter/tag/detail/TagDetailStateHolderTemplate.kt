package io.github.taetae98coding.diary.presenter.tag.detail

import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public class TagDetailStateHolderTemplate(
    private val strategy: TagDetailStrategy,
    private val coroutineScope: CoroutineScope = MainScope(),
) : TagDetailStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    private val tag = strategy.getTag()
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    override val detail: StateFlow<TagDetail?> = tag.mapLatest { result ->
        result.fold(
            onSuccess = { it?.detail },
            onFailure = { null },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    override val finishUiState: StateFlow<TagDetailFinishUiState> = tag.mapLatest { result ->
        result.fold(
            onSuccess = { TagDetailFinishUiState(isFinished = it?.isFinished ?: false) },
            onFailure = { TagDetailFinishUiState() },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TagDetailFinishUiState(),
    )

    private val _floatingUiState = MutableStateFlow(TagDetailFloatingUiState())
    override val floatingUiState: StateFlow<TagDetailFloatingUiState> = _floatingUiState.asStateFlow()

    private val _effect: MutableStateFlow<TagDetailEffect> = MutableStateFlow(TagDetailEffect.None)
    override val effect: StateFlow<TagDetailEffect> = _effect.asStateFlow()

    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure { _effect.emit(TagDetailEffect.FetchError) }
            isInFetchProgress = false
        }
    }

    override fun finish() {
        coroutineScope.launch {
            strategy.finishTag()
                .onFailure { _effect.emit(TagDetailEffect.UnknownError) }
        }
    }

    override fun restart() {
        coroutineScope.launch {
            strategy.restartTag()
                .onFailure { _effect.emit(TagDetailEffect.UnknownError) }
        }
    }

    override fun delete() {
        coroutineScope.launch {
            strategy.deleteTag()
                .onSuccess { _effect.emit(TagDetailEffect.DeleteFinish) }
                .onFailure { _effect.emit(TagDetailEffect.UnknownError) }
        }
    }

    override fun update(detail: TagDetail) {
        if (floatingUiState.value.isInProgress) return

        coroutineScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            strategy.updateTag(detail)
                .onSuccess { _effect.emit(TagDetailEffect.UpdateFinish) }
                .onFailure { _effect.emit(TagDetailEffect.UnknownError) }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(TagDetailEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

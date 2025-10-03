package io.github.taetae98coding.diary.presenter.memo.detail

import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
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

public class MemoDetailStateHolderTemplate(
    private val strategy: MemoDetailStrategy,
    private val coroutineScope: CoroutineScope = MainScope(),
) : MemoDetailStateHolder,
    AutoCloseable {
    private val memo = strategy.getMemo()
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    override val detail: StateFlow<MemoDetail?> = memo.mapLatest { result ->
        result.fold(
            onSuccess = { it?.detail },
            onFailure = { null },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    override val finishUiState: StateFlow<MemoDetailFinishUiState> = memo.mapLatest { result ->
        result.fold(
            onSuccess = { MemoDetailFinishUiState(isFinished = it?.isFinished ?: false) },
            onFailure = { MemoDetailFinishUiState() },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MemoDetailFinishUiState(),
    )

    private val _floatingUiState = MutableStateFlow(MemoDetailFloatingUiState())
    override val floatingUiState: StateFlow<MemoDetailFloatingUiState> = _floatingUiState.asStateFlow()

    private val _effect: MutableStateFlow<MemoDetailEffect> = MutableStateFlow(MemoDetailEffect.None)
    override val effect: StateFlow<MemoDetailEffect> = _effect.asStateFlow()

    override fun finish() {
        coroutineScope.launch {
            strategy.finishMemo()
                .onFailure { _effect.emit(MemoDetailEffect.UnknownError) }
        }
    }

    override fun restart() {
        coroutineScope.launch {
            strategy.restartMemo()
                .onFailure { _effect.emit(MemoDetailEffect.UnknownError) }
        }
    }

    override fun delete() {
        coroutineScope.launch {
            strategy.deleteMemo()
                .onSuccess { _effect.emit(MemoDetailEffect.DeleteFinish) }
                .onFailure { _effect.emit(MemoDetailEffect.UnknownError) }
        }
    }

    override fun update(detail: MemoDetail) {
        if (floatingUiState.value.isInProgress) return

        coroutineScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            strategy.updateMemo(detail)
                .onSuccess { _effect.emit(MemoDetailEffect.UpdateFinish) }
                .onFailure { _effect.emit(MemoDetailEffect.UnknownError) }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    override fun fetch() {
        coroutineScope.launch {
            strategy.fetchMemo().onFailure { _effect.emit(MemoDetailEffect.FetchError) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(MemoDetailEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

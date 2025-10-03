package io.github.taetae98coding.diary.presenter.tag.add

import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.domain.tag.exception.TagTitleBlankException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public class TagAddStateHolderTemplate(
    private val strategy: TagAddStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : TagAddStateHolder,
    AutoCloseable {

    private val _floatingUiState = MutableStateFlow(TagAddFloatingUiState())
    override val floatingUiState: StateFlow<TagAddFloatingUiState> = _floatingUiState.asStateFlow()

    private val _effect = MutableStateFlow<TagAddEffect>(TagAddEffect.None)
    override val effect: StateFlow<TagAddEffect> = _effect.asStateFlow()

    override fun add(detail: TagDetail) {
        if (floatingUiState.value.isInProgress) return

        coroutineScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            strategy.addTag(detail)
                .onSuccess { _effect.emit(TagAddEffect.AddSuccess) }
                .onFailure { throwable ->
                    Logger.log("[TagAddStateHolderTemplate] 태그 추가 실패 : $throwable")
                    when (throwable) {
                        is TagTitleBlankException -> _effect.emit(TagAddEffect.TitleBlank)
                        else -> _effect.emit(TagAddEffect.UnknownError)
                    }
                }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(TagAddEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

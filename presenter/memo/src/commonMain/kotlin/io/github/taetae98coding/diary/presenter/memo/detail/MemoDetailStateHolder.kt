package io.github.taetae98coding.diary.presenter.memo.detail

import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import kotlinx.coroutines.flow.StateFlow

public interface MemoDetailStateHolder {
    public val detail: StateFlow<MemoDetail?>
    public val finishUiState: StateFlow<MemoDetailFinishUiState>
    public val floatingUiState: StateFlow<MemoDetailFloatingUiState>
    public val effect: StateFlow<MemoDetailEffect>
    public fun fetch()
    public fun finish()
    public fun restart()
    public fun delete()
    public fun update(detail: MemoDetail)
    public fun clearEffect()
}

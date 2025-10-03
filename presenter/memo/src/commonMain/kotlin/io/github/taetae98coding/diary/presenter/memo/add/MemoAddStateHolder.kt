package io.github.taetae98coding.diary.presenter.memo.add

import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolder
import kotlinx.coroutines.flow.StateFlow

public interface MemoAddStateHolder : MemoTagStateHolder {
    public val floatingUiState: StateFlow<MemoAddFloatingUiState>
    public val memoAddEffect: StateFlow<MemoAddEffect>

    public fun add(detail: MemoDetail)
    public fun clearMemoAddEffect()
}

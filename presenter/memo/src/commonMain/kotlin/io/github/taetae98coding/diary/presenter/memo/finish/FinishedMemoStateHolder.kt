package io.github.taetae98coding.diary.presenter.memo.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface FinishedMemoStateHolder {
    public val pagingData: Flow<PagingData<Memo>>
    public val effect: StateFlow<FinishedMemoEffect>

    public fun delete(memoId: Uuid)
    public fun restore(memoId: Uuid)
    public fun clearEffect()
}

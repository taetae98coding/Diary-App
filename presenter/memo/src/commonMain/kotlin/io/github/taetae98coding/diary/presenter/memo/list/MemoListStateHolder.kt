package io.github.taetae98coding.diary.presenter.memo.list

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface MemoListStateHolder {
    public val pagingData: Flow<PagingData<Memo>>
    public val effect: StateFlow<MemoListEffect>

    public fun fetch()
    public fun finish(memoId: Uuid)
    public fun delete(memoId: Uuid)
    public fun restart(memoId: Uuid)
    public fun restore(memoId: Uuid)
    public fun clearEffect()
}

package io.github.taetae98coding.diary.presenter.memo.list

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface MemoListStrategy {
    public fun pageMemo(): Flow<Result<PagingData<Memo>>>
    public suspend fun fetch(): Result<Unit>
    public suspend fun finishMemo(memoId: Uuid): Result<Unit>
    public suspend fun deleteMemo(memoId: Uuid): Result<Unit>
    public suspend fun restartMemo(memoId: Uuid): Result<Unit>
    public suspend fun restoreMemo(memoId: Uuid): Result<Unit>
}

package io.github.taetae98coding.diary.presenter.memo.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface FinishedMemoStrategy {
    public fun page(): Flow<Result<PagingData<Memo>>>

    public suspend fun delete(memoId: Uuid): Result<Unit>
    public suspend fun restore(memoId: Uuid): Result<Unit>
}

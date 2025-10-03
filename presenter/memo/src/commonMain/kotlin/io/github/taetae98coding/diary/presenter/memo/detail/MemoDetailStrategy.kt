package io.github.taetae98coding.diary.presenter.memo.detail

import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import kotlinx.coroutines.flow.Flow

public interface MemoDetailStrategy {

    public fun getMemo(): Flow<Result<Memo?>>
    public suspend fun fetch(): Result<Unit>
    public suspend fun finishMemo(): Result<Unit>
    public suspend fun restartMemo(): Result<Unit>
    public suspend fun deleteMemo(): Result<Unit>
    public suspend fun updateMemo(detail: MemoDetail): Result<Unit>
}

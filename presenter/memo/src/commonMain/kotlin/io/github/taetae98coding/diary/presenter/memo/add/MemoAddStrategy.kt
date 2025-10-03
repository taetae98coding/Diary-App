package io.github.taetae98coding.diary.presenter.memo.add

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface MemoAddStrategy {
    public fun pageTag(): Flow<Result<PagingData<Tag>>>
    public suspend fun fetchMemoTag(): Result<Unit>
    public suspend fun addMemo(detail: MemoDetail, primaryTag: Uuid?, memoTagIds: Set<Uuid>): Result<Unit>
}

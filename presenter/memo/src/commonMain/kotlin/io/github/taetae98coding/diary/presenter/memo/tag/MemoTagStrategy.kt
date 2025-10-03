package io.github.taetae98coding.diary.presenter.memo.tag

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface MemoTagStrategy {
    public fun getMemo(): Flow<Result<Memo?>>
    public fun pageSelectMemoTag(): Flow<Result<PagingData<SelectMemoTag>>>

    public suspend fun fetch(): Result<Unit>
    public suspend fun selectPrimaryTag(tagId: Uuid): Result<Unit>
    public suspend fun removePrimaryTag(): Result<Unit>
    public suspend fun selectMemoTag(tagId: Uuid): Result<Unit>
    public suspend fun unselectMemoTag(tagId: Uuid): Result<Unit>
}

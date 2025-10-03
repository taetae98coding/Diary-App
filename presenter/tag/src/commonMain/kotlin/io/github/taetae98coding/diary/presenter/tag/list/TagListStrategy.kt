package io.github.taetae98coding.diary.presenter.tag.list

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface TagListStrategy {
    public fun pageTag(): Flow<Result<PagingData<Tag>>>
    public suspend fun fetch(): Result<Unit>
    public suspend fun finishTag(tagId: Uuid): Result<Unit>
    public suspend fun deleteTag(tagId: Uuid): Result<Unit>
    public suspend fun restartTag(tagId: Uuid): Result<Unit>
    public suspend fun restoreTag(tagId: Uuid): Result<Unit>
}

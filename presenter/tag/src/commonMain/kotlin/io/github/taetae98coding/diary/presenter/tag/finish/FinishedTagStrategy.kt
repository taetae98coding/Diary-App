package io.github.taetae98coding.diary.presenter.tag.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface FinishedTagStrategy {
    public fun page(): Flow<Result<PagingData<Tag>>>
    public suspend fun delete(tagId: Uuid): Result<Unit>
    public suspend fun restore(tagId: Uuid): Result<Unit>
}

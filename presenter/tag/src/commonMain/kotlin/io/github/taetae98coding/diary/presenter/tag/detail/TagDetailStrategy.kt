package io.github.taetae98coding.diary.presenter.tag.detail

import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import kotlinx.coroutines.flow.Flow

public interface TagDetailStrategy {
    public fun getTag(): Flow<Result<Tag?>>
    public suspend fun fetch(): Result<Unit>
    public suspend fun finishTag(): Result<Unit>
    public suspend fun restartTag(): Result<Unit>
    public suspend fun deleteTag(): Result<Unit>
    public suspend fun updateTag(detail: TagDetail): Result<Unit>
}

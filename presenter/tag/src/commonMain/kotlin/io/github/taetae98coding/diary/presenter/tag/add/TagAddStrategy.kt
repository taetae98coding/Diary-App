package io.github.taetae98coding.diary.presenter.tag.add

import io.github.taetae98coding.diary.core.entity.tag.TagDetail

public interface TagAddStrategy {
    public suspend fun addTag(detail: TagDetail): Result<Unit>
}

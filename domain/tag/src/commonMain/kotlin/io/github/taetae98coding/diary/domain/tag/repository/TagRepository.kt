package io.github.taetae98coding.diary.domain.tag.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface TagRepository {
    public fun get(tagId: Uuid): Flow<Tag?>
}

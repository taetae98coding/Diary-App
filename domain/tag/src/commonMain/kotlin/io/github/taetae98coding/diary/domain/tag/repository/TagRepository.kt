package io.github.taetae98coding.diary.domain.tag.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface TagRepository {
    public suspend fun fetch(account: Account.User, tagId: Uuid)
    public fun get(tagId: Uuid): Flow<Tag?>
}

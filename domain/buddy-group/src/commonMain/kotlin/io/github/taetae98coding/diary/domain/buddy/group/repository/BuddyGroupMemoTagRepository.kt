package io.github.taetae98coding.diary.domain.buddy.group.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import kotlin.uuid.Uuid

public interface BuddyGroupMemoTagRepository {
    public suspend fun updateMemoTag(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid, isMemoTag: Boolean)
}

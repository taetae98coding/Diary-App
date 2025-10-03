package io.github.taetae98coding.diary.domain.buddy.group.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import kotlin.uuid.Uuid

public interface BuddyGroupDiaryRepository {
    public suspend fun fetch(account: Account.User, buddyGroupId: Uuid)
}

package io.github.taetae98coding.diary.domain.buddy.group.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface BuddyGroupBuddyRepository {
    public fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Buddy>>
}

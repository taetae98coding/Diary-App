package io.github.taetae98coding.diary.domain.buddy.group.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface BuddyGroupRepository {
    public suspend fun fetch(account: Account.User, buddyGroupId: Uuid)
    public suspend fun add(account: Account.User, detail: BuddyGroupDetail, buddyIds: Collection<Uuid>)
    public suspend fun update(account: Account.User, buddyGroupId: Uuid, detail: BuddyGroupDetail)

    public fun get(buddyGroupId: Uuid): Flow<BuddyGroup?>
    public fun page(account: Account.User): Flow<PagingData<BuddyGroup>>
}

package io.github.taetae98coding.diary.domain.buddy.group.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface BuddyGroupTagRepository {
    public suspend fun add(account: Account.User, buddyGroupId: Uuid, detail: TagDetail)
    public suspend fun update(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, detail: TagDetail)
    public suspend fun updateFinished(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, isFinished: Boolean)
    public suspend fun updateDeleted(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, isDeleted: Boolean)
    public fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Tag>>
}

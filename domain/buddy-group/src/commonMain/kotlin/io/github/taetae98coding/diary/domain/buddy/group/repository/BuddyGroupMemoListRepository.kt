package io.github.taetae98coding.diary.domain.buddy.group.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface BuddyGroupMemoListRepository {
    public fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Memo>>
}

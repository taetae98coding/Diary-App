package io.github.taetae98coding.diary.domain.buddy.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import kotlinx.coroutines.flow.Flow

public interface BuddyRepository {
    public fun search(account: Account, query: String): Flow<PagingData<Buddy>>
}

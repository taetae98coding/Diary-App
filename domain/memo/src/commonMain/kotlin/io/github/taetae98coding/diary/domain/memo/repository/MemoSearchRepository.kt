package io.github.taetae98coding.diary.domain.memo.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlinx.coroutines.flow.Flow

public interface MemoSearchRepository {
    public fun search(account: Account, query: String): Flow<PagingData<Memo>>
}




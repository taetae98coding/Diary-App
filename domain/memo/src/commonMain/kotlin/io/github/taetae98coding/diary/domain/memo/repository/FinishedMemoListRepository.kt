package io.github.taetae98coding.diary.domain.memo.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlinx.coroutines.flow.Flow

public interface FinishedMemoListRepository {
    public fun page(account: Account): Flow<PagingData<Memo>>
}

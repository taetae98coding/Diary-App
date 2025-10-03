package io.github.taetae98coding.diary.domain.tag.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlinx.coroutines.flow.Flow

public interface FinishedTagRepository {
    public fun page(account: Account): Flow<PagingData<Tag>>
}

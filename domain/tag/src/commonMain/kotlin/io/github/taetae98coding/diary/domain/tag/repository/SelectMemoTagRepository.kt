package io.github.taetae98coding.diary.domain.tag.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface SelectMemoTagRepository {
    public fun page(account: Account, memoId: Uuid): Flow<PagingData<SelectMemoTag>>
}

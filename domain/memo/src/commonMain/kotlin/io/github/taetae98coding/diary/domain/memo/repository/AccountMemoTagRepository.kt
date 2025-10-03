package io.github.taetae98coding.diary.domain.memo.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface AccountMemoTagRepository {
    public suspend fun updateMemoTag(account: Account, memoId: Uuid, tagId: Uuid, isMemoTag: Boolean)
}

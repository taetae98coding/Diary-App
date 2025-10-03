package io.github.taetae98coding.diary.domain.tag.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface AccountTagRepository {
    public suspend fun add(account: Account, tag: Tag)
    public suspend fun update(account: Account, tagId: Uuid, detail: TagDetail)
    public suspend fun updateFinished(account: Account, tagId: Uuid, isFinished: Boolean)
    public suspend fun updateDeleted(account: Account, tagId: Uuid, isDeleted: Boolean)
    public fun page(account: Account): Flow<PagingData<Tag>>
}

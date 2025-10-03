package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupBuddyRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.pagingsource.LimitOffsetPagingSource
import kotlin.uuid.Uuid

internal class BuddyGroupBuddyPagingSource(
    private val account: Account.User,
    private val buddyGroupId: Uuid,
    private val buddyGroupBuddyRemoteDataSource: BuddyGroupBuddyRemoteDataSource,
) : LimitOffsetPagingSource<Buddy>() {
    override suspend fun paging(limit: Int, offset: Int): Page<Buddy> {
        val response = buddyGroupBuddyRemoteDataSource.page(account.token, buddyGroupId, limit, offset)
            .requireSuccess()
            .requireBody()

        return Page(
            count = response.count,
            data = response.data.map(BuddyRemoteEntity::toEntity),
        )
    }
}

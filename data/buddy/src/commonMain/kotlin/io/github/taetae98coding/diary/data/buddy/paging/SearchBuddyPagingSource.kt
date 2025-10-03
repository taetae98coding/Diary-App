package io.github.taetae98coding.diary.data.buddy.paging

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.service.datasource.BuddyRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.pagingsource.LimitOffsetPagingSource

internal class SearchBuddyPagingSource(
    private val account: Account,
    private val query: String,
    private val buddyRemoteDataSource: BuddyRemoteDataSource,
) : LimitOffsetPagingSource<Buddy>() {
    override suspend fun paging(limit: Int, offset: Int): Page<Buddy> {
        val paging = buddyRemoteDataSource.search(account.token, query, limit, offset)
            .requireSuccess()
            .requireBody()

        return Page(
            count = paging.count,
            data = paging.data.map(BuddyRemoteEntity::toEntity),
        )
    }
}

package io.github.taetae98coding.diary.data.buddy.group.fetcher

import io.github.taetae98coding.diary.core.entity.account.Account
import kotlin.uuid.Uuid
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.core.annotation.Single

@Single
internal class BuddyGroupDiaryFetcherManager(
    private val memoFetcher: BuddyGroupMemoFetcher,
    private val tagFetcher: BuddyGroupTagFetcher,
    private val memoTagFetcher: BuddyGroupMemoTagFetcher,
) {
    private val mutex = Mutex()

    // TODO refactor
    suspend fun fetch(account: Account.User, buddyGroupId: Uuid) {
        mutex.withLock {
            tagFetcher.fetch(account, buddyGroupId)
            memoFetcher.fetch(account, buddyGroupId)
            memoTagFetcher.fetch(account, buddyGroupId)
        }
    }
}

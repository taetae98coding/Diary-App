package io.github.taetae98coding.diary.data.buddy.group.fetcher

import io.github.taetae98coding.diary.core.entity.account.Account
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal interface BuddyGroupFetcher<Local, Remote> {
    suspend fun fetch(account: Account.User, buddyGroupId: Uuid) {
        while (true) {
            val remote = pull(
                account = account,
                buddyGroupId = buddyGroupId,
                updatedAt = getLastUpdateAt(buddyGroupId).first() ?: -1,
            )

            if (remote.isEmpty()) {
                break
            }

            push(account, buddyGroupId, remote.map(::mapper))
        }
    }

    fun mapper(remote: Remote): Local
    fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?>
    suspend fun pull(account: Account.User, buddyGroupId: Uuid, updatedAt: Long): List<Remote>
    suspend fun push(account: Account.User, buddyGroupId: Uuid, data: List<Local>)
}

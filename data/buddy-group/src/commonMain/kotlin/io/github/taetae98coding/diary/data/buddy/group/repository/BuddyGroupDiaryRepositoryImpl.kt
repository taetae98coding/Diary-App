package io.github.taetae98coding.diary.data.buddy.group.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.data.buddy.group.fetcher.BuddyGroupDiaryFetcherManager
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupDiaryRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupDiaryRepositoryImpl(
    private val fetcherManager: BuddyGroupDiaryFetcherManager,
) : BuddyGroupDiaryRepository {
    override suspend fun fetch(account: Account.User, buddyGroupId: Uuid) {
        fetcherManager.fetch(account, buddyGroupId)
    }
}

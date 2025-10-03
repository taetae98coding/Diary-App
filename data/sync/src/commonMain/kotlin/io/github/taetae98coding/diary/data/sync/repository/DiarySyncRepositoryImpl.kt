package io.github.taetae98coding.diary.data.sync.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.manager.DiarySyncWorkManager
import io.github.taetae98coding.diary.domain.sync.repository.DiarySyncRepository
import org.koin.core.annotation.Factory

@Factory
internal class DiarySyncRepositoryImpl(
    private val manager: DiarySyncWorkManager,
) : DiarySyncRepository {
    override suspend fun requestSync(account: Account.User) {
        manager.requestSync(account)
    }

    override suspend fun sync(account: Account.User) {
        manager.sync(account)
    }
}

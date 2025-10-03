package io.github.taetae98coding.diary.data.sync.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.work.manager.DiaryBackupWorkManager
import io.github.taetae98coding.diary.domain.sync.repository.DiaryBackupRepository
import org.koin.core.annotation.Factory

@Factory
internal class DiaryBackupRepositoryImpl(
    private val manager: DiaryBackupWorkManager,
) : DiaryBackupRepository {
    override suspend fun requestBackup(account: Account.User) {
        manager.requestBackup(account)
    }

    override suspend fun backup(account: Account.User) {
        manager.backup(account)
    }
}

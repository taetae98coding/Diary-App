package io.github.taetae98coding.diary.domain.sync.repository

import io.github.taetae98coding.diary.core.entity.account.Account

public interface DiaryBackupRepository {
    public suspend fun requestBackup(account: Account.User)
    public suspend fun backup(account: Account.User)
}

package io.github.taetae98coding.diary.core.work.manager

import io.github.taetae98coding.diary.core.entity.account.Account

public interface DiaryBackupWorkManager {
    public fun requestBackup(account: Account.User)
    public suspend fun backup(account: Account.User)
}

package io.github.taetae98coding.diary.core.work.manager

import io.github.taetae98coding.diary.core.entity.account.Account

public interface DiarySyncWorkManager {
    public fun requestSync(account: Account.User)
    public suspend fun sync(account: Account.User)
}

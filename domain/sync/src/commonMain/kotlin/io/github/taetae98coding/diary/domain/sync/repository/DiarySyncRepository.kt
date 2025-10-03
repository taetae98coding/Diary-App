package io.github.taetae98coding.diary.domain.sync.repository

import io.github.taetae98coding.diary.core.entity.account.Account

public interface DiarySyncRepository {
    public suspend fun requestSync(account: Account.User)
    public suspend fun sync(account: Account.User)
}

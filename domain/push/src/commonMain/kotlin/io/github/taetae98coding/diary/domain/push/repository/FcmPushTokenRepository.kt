package io.github.taetae98coding.diary.domain.push.repository

import io.github.taetae98coding.diary.core.entity.account.Account

public interface FcmPushTokenRepository {
    public suspend fun register(account: Account)
}

package io.github.taetae98coding.diary.data.push.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.service.datasource.FcmRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.push.FcmPushTokenRemoteEntity
import io.github.taetae98coding.diary.domain.push.repository.FcmPushTokenRepository
import io.github.taetae98coding.diary.library.firebase.core.KmpFirebase
import io.github.taetae98coding.diary.library.firebase.messaging.messaging
import org.koin.core.annotation.Factory

@Factory
internal class FcmPushTokenRepositoryImpl(
    private val fcmRemoteDataSource: FcmRemoteDataSource,
) : FcmPushTokenRepository {
    override suspend fun register(account: Account) {
        fcmRemoteDataSource.register(account.token, FcmPushTokenRemoteEntity(KmpFirebase.messaging.token()))
    }
}

package io.github.taetae98coding.diary.library.firebase.messaging.internal

import cocoapods.FirebaseMessaging.FIRMessaging
import io.github.taetae98coding.diary.library.firebase.messaging.KmpFirebaseMessaging

internal class KmpFirebaseMessagingImpl : KmpFirebaseMessaging {
    override suspend fun token(): String {
        val token = FIRMessaging.messaging().FCMToken()

        return requireNotNull(token)
    }
}

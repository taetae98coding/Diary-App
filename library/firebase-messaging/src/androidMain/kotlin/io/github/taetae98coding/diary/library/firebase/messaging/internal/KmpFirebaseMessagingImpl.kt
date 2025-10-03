package io.github.taetae98coding.diary.library.firebase.messaging.internal

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import io.github.taetae98coding.diary.library.firebase.messaging.KmpFirebaseMessaging
import kotlinx.coroutines.tasks.await

internal class KmpFirebaseMessagingImpl : KmpFirebaseMessaging {
    override suspend fun token(): String {
        return Firebase.messaging.token.await()
    }
}

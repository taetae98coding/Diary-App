package io.github.taetae98coding.diary.library.firebase.messaging

import io.github.taetae98coding.diary.library.firebase.core.KmpFirebase
import io.github.taetae98coding.diary.library.firebase.messaging.internal.KmpFirebaseMessagingImpl

public actual val KmpFirebase.messaging: KmpFirebaseMessaging
    get() = KmpFirebaseMessagingImpl()

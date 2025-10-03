package io.github.taetae98coding.diary.service

import android.Manifest
import android.content.pm.PackageManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.github.taetae98coding.diary.core.notification.BuddyGroupNotificationManager
import io.github.taetae98coding.diary.core.notification.DiaryNotificationManager
import io.github.taetae98coding.diary.domain.push.usecase.RegisterFcmPushTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

internal class DiaryFirebaseMessagingService : FirebaseMessagingService() {
    private val scope = CoroutineScope(SupervisorJob())

    private val registerFcmPushTokenUseCase by inject<RegisterFcmPushTokenUseCase>()

    private val buddyGroupNotificationManager by inject<BuddyGroupNotificationManager>()
    private val diaryNotificationManager by inject<DiaryNotificationManager>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        scope.launch { registerFcmPushTokenUseCase() }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            when (message.data["type"]) {
                BUDDY_GROUP_TYPE -> buddyGroupNotificationManager.notify(message.notification?.title, message.notification?.body)
                else -> diaryNotificationManager.notify(message.notification?.title, message.notification?.body)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val BUDDY_GROUP_TYPE = "BuddyGroup"
    }
}

package io.github.taetae98coding.diary.core.notification

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupNotificationManager internal constructor(
    private val context: Context,
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public fun notify(title: String?, description: String?) {
        val manager = NotificationManagerCompat.from(context)

        val channel = NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName("Diary")
            .setDescription("Buddy Group notification channel")
            .setLightsEnabled(true)
            .setVibrationEnabled(true)
            .setShowBadge(true)
            .build()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_android)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        manager.createNotificationChannel(channel)
        manager.notify(0, notification)
    }

    public companion object Companion {
        private const val CHANNEL_ID = "BuddyGroup"
    }
}

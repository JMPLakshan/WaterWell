package com.example.waterwell

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.example.waterwell.notifications.NotificationHelper

class WaterWellApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("WaterWellApp", "🚀 WaterWellApp initialized")
        createHydrationChannel()
    }

    /**
     * ✅ Creates a dedicated notification channel for hydration reminders.
     * Required for Android 8.0 (API 26) and above.
     */
    private fun createHydrationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = NotificationHelper.CHANNEL_HYDRATION_ID
            val channelName = "Hydration Reminders"
            val descriptionText = "Reminds you to drink water regularly"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
                enableVibration(true)
                enableLights(true)
                setShowBadge(true)
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            Log.d("WaterWellApp", "✅ Notification channel created: $channelName")
        } else {
            Log.d("WaterWellApp", "ℹ️ No channel needed (SDK < 26)")
        }
    }
}

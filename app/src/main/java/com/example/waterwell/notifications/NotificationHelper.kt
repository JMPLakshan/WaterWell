package com.example.waterwell.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.waterwell.R
import com.example.waterwell.ui.MainActivity   // ✅ Correct package reference

object NotificationHelper {

    const val CHANNEL_HYDRATION_ID = "hydration_channel"
    private const val NOTIF_ID = 1001

    fun showHydrationReminder(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_HYDRATION_ID)
            .setSmallIcon(R.drawable.ic_water)
            .setContentTitle("Stay Hydrated 💧")
            .setContentText("Time to drink some water!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIF_ID, notification)
    }
}

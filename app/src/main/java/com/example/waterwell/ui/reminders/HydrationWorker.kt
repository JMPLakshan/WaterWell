package com.example.waterwell.ui.reminders

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.waterwell.R
import kotlin.random.Random

class HydrationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val notif = NotificationCompat.Builder(applicationContext, "hydration")
            .setSmallIcon(R.drawable.ic_water)
            .setContentTitle("Time to hydrate 💧")
            .setContentText("Drink a glass of water.")
            .setAutoCancel(true)
            .build()

        if (NotificationManagerCompat.from(applicationContext)
                .areNotificationsEnabled()
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(Random.nextInt(), notif)
        }
        return Result.success()
    }
}

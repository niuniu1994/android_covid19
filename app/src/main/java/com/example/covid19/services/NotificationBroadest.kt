package com.example.covid19.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.covid19.MainActivity
import com.example.covid19.R


/**
 * Notification
 */
class NotificationBroadest : BroadcastReceiver() {
    private val CHANNEL_ID = "CovidDailyNotificationId"
    private val NOTIFATIONID = 9527

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent1 = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent1)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setContentTitle("Daily Covid Info updated")
            .setContentText("Please the latest Covid info")
            .setSmallIcon(R.drawable.virus)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFATIONID, notification)
        }
    }


}
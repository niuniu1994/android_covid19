package com.example.covid19.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.covid19.MainActivity
import com.example.covid19.R

/**
 * Service occupied sending the daily notification repetitively
 */
class NotificationService: Service(){
    private val CHANNEL_ID = "CovidDailyNotificationId"
    private val CHANNEL_NAME = "CovidDailyNotification"

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intent1 = Intent(this,NotificationBroadest::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent1,0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
        }


        // Invoke notification of daily update every day at midnight
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        return START_NOT_STICKY
    }

    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}
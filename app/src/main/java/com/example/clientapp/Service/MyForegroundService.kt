package com.example.clientapp.Service

// MyForegroundService.kt
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.clientapp.R

class MyForegroundService : Service() {

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_account) // Add your own icon here
            .build()

        startForeground(1, notification)
        Log.d("MyForegroundService", "Service created")
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Return null if you don't need binding
        Log.d("MyForegroundService", "Service onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyForegroundService", "Service onStartCommand")
        // Do the work you need here
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            Log.d("MyForegroundService", "Service createNotificationChannel")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyForegroundService", "Service destroyed")
    }
}

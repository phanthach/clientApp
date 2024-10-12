package com.example.clientapp.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.clientapp.Presentation.UI.Main.UserActivity
import com.example.clientapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Xử lý thông báo nhận được
        remoteMessage.notification?.let {
            val title = it.title
            val body = it.body
            sendNotification(title, body)
        }

        // Xử lý dữ liệu nếu có
        remoteMessage.data.isNotEmpty().let {
            val data = remoteMessage.data
            // Xử lý dữ liệu ở đây
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        val channelId = "your_channel_id"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo Notification Channel cho Android 8.0 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Your Channel Name"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = "Channel description"
            notificationManager.createNotificationChannel(channel)
        }

        // Tạo một Intent để mở ứng dụng khi người dùng nhấn vào thông báo
        val intent = Intent(this, UserActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // Tạo thông báo
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_home) // Thay đổi với biểu tượng của bạn
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }
}

package com.example.clientapp.Domain.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.clientapp.Domain.Repository.TokenRepository
import com.example.clientapp.Domain.Repository.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var tokenRepository: TokenRepository
    @Inject
    lateinit var userRepository: UserRepository
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Kiểm tra nếu thông báo có dữ liệu hoặc chỉ có thông báo
        remoteMessage.notification?.let {
            // Gửi thông báo đến người dùng
            sendNotification(it.body ?: "No message")
        }
    }

    private fun sendNotification(messageBody: String) {
        // Tạo ID cho thông báo
        val notificationId = 0
        val channelId = "default_channel"
        val channelName = "FCM Notifications"

        // Cài đặt NotificationChannel cho Android 8.0 (API level 26) trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH // Ưu tiên cao
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Tạo thông báo với BigTextStyle để hiển thị nội dung dài
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("New Message")
            .setContentText(messageBody) // Hiển thị ngắn gọn trên giao diện thu gọn
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody)) // Hiển thị đầy đủ khi mở rộng
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Ưu tiên cao
            .setAutoCancel(true)

        // Hiển thị thông báo
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Lưu token vào SharedPreferences
        saveTokenToSharedPreferences(token)

        // Gửi token lên server
        val tokenAuth = "Bearer " + tokenRepository.getToken()
        sendTokenToServer(tokenAuth, token)
    }

    private fun saveTokenToSharedPreferences(token: String) {
        tokenRepository.saveFCMToken(token)
    }

    private fun sendTokenToServer(tokenAuth: String,token: String) {
        GlobalScope.launch(Dispatchers.IO) {
            userRepository.saveFCMToken(tokenAuth, token)
        }
    }

}

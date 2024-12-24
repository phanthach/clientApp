package com.example.clientapp.Domain.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.clientapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var isDownloading = false

    override fun onBind(intent: Intent?): IBinder? {
        // Không hỗ trợ bind
        return null
    }

    override fun onCreate() {
        super.onCreate()

        // Tạo NotificationManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo NotificationChannel (cho Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "DOWNLOAD_CHANNEL",
                "Download Notifications",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Chuẩn bị Notification
        notificationBuilder = NotificationCompat.Builder(this, "DOWNLOAD_CHANNEL")
            .setContentTitle("Downloading...")
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(100, 0, false)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val fileUrl = intent?.getStringExtra("fileUrl") ?: return START_NOT_STICKY
        val fileName = intent.getStringExtra("fileName") ?: "downloaded_file.pdf"

        if (!isDownloading) {
            isDownloading = true
            startForeground(1, notificationBuilder.build())
            downloadFile(fileUrl, fileName)
        }

        return START_NOT_STICKY
    }

    private fun downloadFile(fileUrl: String, fileName: String) {
        val savePath = File(filesDir, fileName)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = URL(fileUrl).openConnection() as HttpURLConnection
                val fileSize = connection.contentLength
                val inputStream = connection.inputStream
                val outputStream = FileOutputStream(savePath)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                var totalBytesRead = 0

                // Đọc dữ liệu và cập nhật tiến trình
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead

                    val progress = (totalBytesRead * 100 / fileSize)
                    updateNotificationProgress(progress)
                }

                outputStream.close()
                inputStream.close()
                connection.disconnect()

                onDownloadComplete(savePath.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
                onDownloadFailed()
            }
        }
    }

    private fun updateNotificationProgress(progress: Int) {
        notificationBuilder.setProgress(100, progress, false)
            .setContentText("Progress: $progress%")
        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun onDownloadComplete(filePath: String) {
        isDownloading = false
        notificationBuilder.setContentText("Download Complete")
            .setProgress(0, 0, false)
        notificationManager.notify(1, notificationBuilder.build())
        stopSelf()
    }

    private fun onDownloadFailed() {
        isDownloading = false
        notificationBuilder.setContentText("Download Failed")
            .setProgress(0, 0, false)
        notificationManager.notify(1, notificationBuilder.build())
        stopSelf()
    }
}

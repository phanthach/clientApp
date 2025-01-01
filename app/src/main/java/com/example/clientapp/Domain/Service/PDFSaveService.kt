package com.example.clientapp.Domain.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.clientapp.R
import java.io.File
import java.io.IOException

class PDFSaveService : Service() {

    companion object {
        const val CHANNEL_ID = "PDFSaveChannel"
        const val NOTIFICATION_ID = 1
        const val EXTRA_FILE_PATH = "extra_file_path"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filePath = intent?.getStringExtra(EXTRA_FILE_PATH)
        filePath?.let {
            val file = File(it)
            if (file.exists()) {
                saveBitmapToPDF(file)
            } else {
                showError("File không tồn tại")
            }
        }
        return START_NOT_STICKY
    }

    private fun saveBitmapToPDF(file: File) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "PDF Download",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Downloading PDF")
            .setProgress(100, 0, true)
            .setAutoCancel(true) // Cho phép vuốt xóa thông báo
            .setPriority(NotificationCompat.PRIORITY_LOW)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

        try {
            // Tạo tài liệu PDF từ file ảnh
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            if (bitmap != null) {
                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                page.canvas.drawBitmap(bitmap, 0f, 0f, null)
                pdfDocument.finishPage(page)

                // Lưu file PDF vào MediaStore
                val fileName = "ticket_${System.currentTimeMillis()}.pdf"
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                }

                val resolver = contentResolver
                val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

                uri?.let {
                    resolver.openOutputStream(it).use { outputStream ->
                        pdfDocument.writeTo(outputStream)
                    }
                } ?: throw IOException("Không thể tạo URI")

                pdfDocument.close()

                // Cập nhật thông báo tiến trình thành công
                notificationBuilder.setContentText("Lưu thành công")
                    .setProgress(0, 0, false)
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            } else {
                showError("Không thể đọc ảnh từ file")
            }
        } catch (e: IOException) {
            e.printStackTrace()

            // Cập nhật thông báo khi thất bại
            showError("Lỗi khi lưu PDF")
        } finally {
            stopForeground(true)
            stopSelf()
        }
    }

    private fun showError(message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Lỗi")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setProgress(0, 0, false)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Chỉ cần tạo kênh thông báo trên Android 8.0 trở lên
            val channel = NotificationChannel(
                CHANNEL_ID,
                "PDF Save Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Thông báo khi lưu PDF"
            }

            // Tạo kênh thông báo
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
    override fun onBind(intent: Intent?) = null
}


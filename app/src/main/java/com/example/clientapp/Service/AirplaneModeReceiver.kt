package com.example.clientapp.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = intent.getBooleanExtra("state", false)
            if (isAirplaneModeOn) {
                // Máy bay mode đã bật
                Toast.makeText(context, "Chế độ máy bay đã bật", Toast.LENGTH_SHORT).show()
            } else {
                // Máy bay mode đã tắt
                Toast.makeText(context, "Chế độ máy bay đã tắt", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

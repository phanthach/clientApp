package com.example.clientapp.Data.Repository

import android.content.SharedPreferences
import android.util.Log
import com.example.clientapp.Domain.Repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor( private val sharedPreferences: SharedPreferences):
    TokenRepository {
        companion object {
            private const val TOKEN_KEY = "tokenKey"
        }

        private var userToken: String? = null
        private var isRemembered = false // Cờ để kiểm tra ghi nhớ đăng nhập

        override fun saveToken(token: String, remember: Boolean) {
            // Lưu token vào bộ nhớ tạm
            userToken = token
            isRemembered = remember
            // Nếu "ghi nhớ đăng nhập" được chọn, lưu token vào SharedPreferences
            if (remember) {
                sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
            }
        }

        override fun getToken(): String? {
            // Kiểm tra nếu token đã tồn tại trong bộ nhớ tạm, nếu không thì lấy từ SharedPreferences
            val savedToken = sharedPreferences.getString("tokenKey", null)
            Log.e("TokenCheck", "Saved Token: $savedToken")
            if (userToken == null && isRemembered) {
                userToken = sharedPreferences.getString(TOKEN_KEY, null)
            }
            return userToken
        }

        override fun deleteToken() {
            // Xóa token trong bộ nhớ và SharedPreferences nếu có
            userToken = null
            if (isRemembered) {
                sharedPreferences.edit().remove(TOKEN_KEY).apply()
            }
            isRemembered = false // Reset lại trạng thái ghi nhớ
        }

    override fun getTokenValid(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}
package com.example.clientapp.Presentation.Pay

import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientapp.Presentation.Main.MainActivity
import com.example.clientapp.Presentation.UserActivity.UserActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btBack.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
        setUpVNPay()
    }

    private fun setUpVNPay() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        binding.webView.loadUrl("https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_Amount=1020000000&vnp_Command=pay&vnp_CreateDate=20241202023222&vnp_CurrCode=VND&vnp_ExpireDate=20241202024722&vnp_IpAddr=10.0.0.139&vnp_Locale=vn&vnp_OrderInfo=Phan+Tuan+Thach+chuyen+khoan&vnp_OrderType=order-type&vnp_ReturnUrl=http%3A%2F%2F10.0.0.139%3A8080%2Fvnpay-payment-return&vnp_TmnCode=ICNPTVL2&vnp_TxnRef=15202412020232224403&vnp_Version=2.1.0&vnp_SecureHash=239bbaff83c364a0111c5d990f622490037479734db86a102d6b510be9fc67e2d53fe2758e2031d94c04278051eae5ece1f75df775586ac36321908f51edda14")
    }
}
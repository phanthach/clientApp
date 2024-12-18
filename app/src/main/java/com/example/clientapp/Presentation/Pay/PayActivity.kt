package com.example.clientapp.Presentation.Pay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientapp.Presentation.Main.MainActivity
import com.example.clientapp.Presentation.UserActivity.UserActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityPayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    private val payActivityViewModel: PayActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btBack.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
        var paymentId = intent.getIntExtra("paymentId", -1)
        Log.d("FragmentPay", "paymentId1: $paymentId")
        getPayment(paymentId)
        binding.btnPaymentLater.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getPayment(paymentId: Int) {
        payActivityViewModel.getPayment(paymentId)
        payActivityViewModel.payment.observe(this, { payment ->
            Log.d("PayActivity", payment.toString())
            if (payment != null) {
                if (payment.paymentId == -1 || payment.paymentUrl == null) {
                    AlertDialog.Builder(this)
                        .setTitle("Lỗi")
                        .setMessage("Đã có lỗi xảy ra, vui lòng thử lại")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(this, UserActivity::class.java)
                            startActivity(intent)
                            finish()
                        }.show()
                }
                else{
                    binding.progressBar.visibility = View.GONE
                    setUpVNPay(payment.paymentUrl)
                    binding.webView.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setUpVNPay(url: String) {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        binding.webView.loadUrl(url)
    }
}
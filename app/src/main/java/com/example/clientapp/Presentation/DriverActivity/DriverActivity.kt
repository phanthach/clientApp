package com.example.clientapp.Presentation.DriverActivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityDriverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriverBinding
    // Mã yêu cầu quyền vị trí
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    // Mã yêu cầu quyền camera
    private val CAMERA_PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverBinding.inflate(layoutInflater)

        // Kiểm tra quyền vị trí
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa cấp quyền, yêu cầu quyền
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }

        // Kiểm tra quyền camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa cấp quyền, yêu cầu quyền
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }

        setContentView(binding.root)
        setUpViewPager()
        setUpBottomNavigation()
    }

    // Xử lý kết quả yêu cầu quyền
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền vị trí đã được cấp
                    Toast.makeText(this, "Quyền vị trí đã được cấp", Toast.LENGTH_SHORT).show()
                } else {
                    // Quyền vị trí bị từ chối
                    Toast.makeText(this, "Quyền vị trí bị từ chối", Toast.LENGTH_SHORT).show()
                }
            }
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền camera đã được cấp
                    Toast.makeText(this, "Quyền camera đã được cấp", Toast.LENGTH_SHORT).show()
                } else {
                    // Quyền camera bị từ chối
                    Toast.makeText(this, "Quyền camera bị từ chối", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    binding.viewpager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    binding.viewpager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account -> {
                    binding.viewpager.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    binding.viewpager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }

    private fun setUpViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.viewpager.adapter = viewPagerAdapter
        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> binding.bottomNavigation.getMenu().findItem(R.id.home).setChecked(true)
                    1 -> binding.bottomNavigation.getMenu().findItem(R.id.history).setChecked(true)
                    else -> binding.bottomNavigation.getMenu().findItem(R.id.account).setChecked(true)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}

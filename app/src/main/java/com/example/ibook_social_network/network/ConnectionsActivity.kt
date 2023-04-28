package com.example.ibook_social_network.network

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ibook_social_network.R
import com.example.ibook_social_network.loading.SplashActivity


class ConnectionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connections)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun settingsWifi(view: View?) {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = true

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            activityResultLauncher.launch(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        else{
            activityResultLauncher.launch(Intent(Settings.Panel.ACTION_WIFI))
        }
    }

    fun settingsMobileData(view: View?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        else activityResultLauncher.launch(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
    }

    fun connected(view: View?) {
        finish()
        startActivity(Intent(this, SplashActivity::class.java))
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            finish()
            startActivity(Intent(this, SplashActivity::class.java))
        }
}
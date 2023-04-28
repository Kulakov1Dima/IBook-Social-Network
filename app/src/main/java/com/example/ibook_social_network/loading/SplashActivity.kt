package com.example.ibook_social_network.loading

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.ibook_social_network.R
import com.example.ibook_social_network.network.ConnectionsActivity
import com.example.ibook_social_network.network.Network

/**
 * Ibook - приложение соцсети.
 * 21.10.2021  01:09 Кулаков Дмитрий
 * 2.7.0 (208)
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Handler().postDelayed({
            val network = Network()

            if (!network.checkConnections(this)) {
                startActivity(Intent(this, ConnectionsActivity::class.java))
                finish()
            }
            else{
                network.checkServer(this)
            }

        }, 100)
    }
}
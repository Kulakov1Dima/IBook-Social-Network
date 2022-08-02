package com.example.ibook_social_network

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

/**
 * Ibook - приложение соцсети.
 * 21.10.2021  01:09 Кулаков Дмитрий
 * 0.1 (205)
 */

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //starting the screen
        Configuration.awp(window, supportActionBar)
        setContentView(R.layout.activity_splash_screen)
        //checking the internet and google account
        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Configuration.checkGoogle(Intent(this, Messenger::class.java), this)
            if (!Configuration.isOnline(this)) {
                intent = Intent(this, NotConnectedToTheInternet::class.java)
            } else if (intent == null) {
                //checking last authorization
                intent = Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 1000) // 1000 is the delayed time in milliseconds.
    }
}
package com.example.ibook_social_network

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ibook_social_network.Configuration.checkServer
import com.google.android.gms.auth.api.signin.GoogleSignIn

/**
 * Ibook - приложение соцсети.
 * 21.10.2021  01:09 Кулаков Дмитрий
 * 2.5.2 (206)
 */

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //start the screen
        Configuration.awp(window, supportActionBar)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            //check internet connection
            if (!Configuration.checkConnections(this)) {
                startActivity(Intent(this, ConnectionsActivity::class.java))
                finish()
            } else {
                //checking the connection to the server and google
                if (checkServer()) {
                    val account = GoogleSignIn.getLastSignedInAccount(this)
                    if(account == null){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else{
                        println(account.email)
                    }
                } else {
                    startActivity(Intent(this, ServerNotFound::class.java))
                    finish()
                }
            }
        }, 100)
    }
}
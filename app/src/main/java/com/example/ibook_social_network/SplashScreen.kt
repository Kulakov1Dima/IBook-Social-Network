package com.example.ibook_social_network

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn

/**
 * Ibook - приложение соцсети.
 * 21.10.2021  01:09 Кулаков Дмитрий
 * 0.1 (205)
 */

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //starting the screen
        awp()
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            if (!checkForInternet(this)) {
                val intent = Intent(this, NotConnectedToTheInternet::class.java)
                startActivity(intent)
            } else {
                val account = GoogleSignIn.getLastSignedInAccount(this)
                if (account != null){
                    MainActivity.email = account.email
                    intent = Intent(this, Messenger::class.java)
                    intent.putExtra("nickname", account.givenName)
                    intent.putExtra("token", MainActivity.email)
                    intent.putExtra("profile_picture", account.photoUrl)
                }
                else {
                    intent = Intent(this, MainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }, 1000) // 1000 is the delayed time in milliseconds.
    }

    //applying window parameters
    private fun awp() {
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
    }

    //checking the availability of the Internet
    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

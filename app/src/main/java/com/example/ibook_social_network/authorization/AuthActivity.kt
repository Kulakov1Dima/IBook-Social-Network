package com.example.ibook_social_network.authorization

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ibook_social_network.R
import com.example.ibook_social_network.loading.SplashActivity
import com.example.ibook_social_network.parameters.GetAccount.getEmail
import com.example.ibook_social_network.parameters.SaveAccount
import com.example.ibook_social_network.registration.RegistrationActivity
import com.yandex.authsdk.YandexAuthException


open class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //проверка ранней авторизации.
        if(getEmail(this)!= null){
            finish()
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        setContentView(R.layout.activity_auth)

    }

    fun yaAuth(view: View) {
        yandexResultLauncher.launch(YandexAuth.auth(this))
    }

    private var yandexResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            try {
                val yandexAuthToken = YandexAuth.sdk.extractToken(result.resultCode, result.data)
                if (yandexAuthToken != null) {
                    Toast.makeText(
                        applicationContext,
                        "Добро пожаловать",
                        Toast.LENGTH_SHORT
                    ).show()
                    SaveAccount.getYandexAccount(this, yandexAuthToken)
                    finish()
                    startActivity(Intent(this, SplashActivity::class.java))
                }
            } catch (e: YandexAuthException) {
                Toast.makeText(
                    applicationContext,
                    "Не получилось авторизоваться: $e",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun googleAuth(view: View) {
        googleResultLauncher.launch((GoogleAuth.GoogleIntent(this)))
    }


    private var googleResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(
                applicationContext,
                "Добро пожаловать",
                Toast.LENGTH_SHORT
            ).show()
            SaveAccount.googleAccount(this)
            finish()
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }
}

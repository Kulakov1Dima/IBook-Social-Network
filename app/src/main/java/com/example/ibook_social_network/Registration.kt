package com.example.ibook_social_network

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //start the screen
        Configuration.awp(window, supportActionBar)
        setContentView(R.layout.activity_registration)
    }
}
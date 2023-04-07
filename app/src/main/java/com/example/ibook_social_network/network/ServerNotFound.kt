package com.example.ibook_social_network.network

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ibook_social_network.R

class ServerNotFound : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //start the screen
        setContentView(R.layout.activity_server_not_found)
    }

    fun connected(view: View?) {
        finish()
        startActivity(Intent(this, ConnectionsActivity::class.java))
    }
}

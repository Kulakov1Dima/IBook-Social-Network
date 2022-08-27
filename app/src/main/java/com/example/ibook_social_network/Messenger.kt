package com.example.ibook_social_network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class Messenger : AppCompatActivity(), SendingPost.Callback {

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onPause()
    }

    override fun onResume() {
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("custom-event-name"))
        super.onResume()
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            Log.d("receiver", "Got message:" + intent.getStringExtra("message"))
            Configuration.listOfLetters(this@Messenger, intent)
        }
    }

    //welcome panel
    private fun welcome(url_photo: String) {
        findViewById<TextView>(R.id.Welcome).text = Configuration.formatGreeting(
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            intent.extras?.get("nickname").toString()
        )
        if (url_photo != "null") {
            Glide.with(this)
                .load(url_photo)
                .into(findViewById<View>(R.id.image_profile) as CircleImageView)
        }
        Configuration.listOfLetters(this, intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.awp(window, Objects.requireNonNull(supportActionBar))
        setContentView(R.layout.activity_messenger)
        welcome(intent.extras?.get("profile_picture").toString())
        Configuration.checkService(this, MessageService::class.java)
    }

    fun onNewMessageAction(view: View) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.adding_new_message)
        bottomSheetDialog.show()
        bottomSheetDialog.findViewById<View>(R.id.regbutton)!!.setOnClickListener {
            if (bottomSheetDialog.findViewById<TextView>(R.id.phone)!!.text.toString() != "" &&
                bottomSheetDialog.findViewById<TextView>(R.id.message)!!.text.toString() != ""
            ) {
                Configuration.sendMessage(
                    this@Messenger,
                    intent.extras?.get("token").toString(),
                    bottomSheetDialog.findViewById<TextView>(R.id.phone)!!.text.toString(),
                    intent.extras?.get("profile_picture").toString(),
                    bottomSheetDialog.findViewById<TextView>(R.id.message)!!.text.toString()
                )
                bottomSheetDialog.cancel()
                Configuration.listOfLetters(this, intent)
            }
        }
    }

    override fun callingBack(s: String?) {
        if (s != null) {
        //    if (s.split(" ")[0] == "get_list:") {
        //        if (s.length != 10) takeList(s.substring(10))
        //    } else {
                Log.e("server", s)
        //}
        }
    }

    private fun takeList(senders: String) {
        for (i in senders.split(" ").indices) {
            Log.e("server", senders.split(" ")[i])
            messageView(senders.split(" "))
        }
    }

    private fun messageView(split: List<String>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomRecyclerAdapter(split.toList(), this)
    }

    companion object {
        fun nextActivity(oldIntent: Intent, messenger: Messenger, name: String) {
            messenger.startActivity(
                Configuration.nextActivity(
                    oldIntent,
                    name,
                    Intent(messenger, Dialogue::class.java)
                )
            )
        }
    }
}
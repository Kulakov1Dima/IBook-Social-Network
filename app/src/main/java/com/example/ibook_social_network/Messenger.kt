package com.example.ibook_social_network


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class Messenger : AppCompatActivity(), SendingPost.Callback {

    private val mHandler = Handler()

    private val badTimeUpdater: Runnable = object : Runnable {
        override fun run() {
            // Log.e ("up", String.valueOf(update));
            if (update) {
                listOfLetters(token)
                update = false
            }
            mHandler.postDelayed(this, 2000)
        }
    }

    companion object {

        @kotlin.jvm.JvmField
        var update: Boolean = false

        fun nextActivity(messenger: Messenger, name: String) {
            val intent = Intent(messenger, Dialogue::class.java)
            intent.putExtra("recipient", name)
            messenger.startActivity(intent)
        }
    }

    private var photo = ""
    private var token = ""

    private lateinit var recyclerView: RecyclerView


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        awp()
        setContentView(R.layout.activity_messenger)
        startService(Intent(this, MessageService::class.java))
        val textView = findViewById<TextView>(R.id.Welcome)
        textView.text = formatGreeting(
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        ) + intent.extras?.get("nickname").toString()
        photo = intent.extras?.get("profile_picture").toString()
        token = intent.extras?.get("token").toString()
        val url: String = photo
        if (url != "null") {
            Glide.with(this)
                .load(url)
                .into(findViewById<View>(R.id.image_profile) as CircleImageView)
        }
        listOfLetters(token)
        mHandler.removeCallbacks(badTimeUpdater)
        mHandler.postDelayed(badTimeUpdater, 1000)
        }

    private fun listOfLetters(email: String) {
        SendingPost(this).execute(
            "http://ibook.agency/message%20storage.php",
            CreateJSON.JSON(
                email, null,
                photo, null
            )
        )
    }

    //applying window parameters
    private fun awp() {
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
    }

    private fun formatGreeting(time: Int): String {
        var greeting = ""
        if (time >= 0) greeting = "Доброй ночи, "
        if (time >= 4) greeting = "Доброе утро, "
        if (time >= 11) greeting = "Добрый день, "
        if (time > 16) greeting = "Добрый вечер, "
        if (time > 21) greeting = "Доброй ночи, "
        return greeting
    }

    fun onNewMessageAction(view: View) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.adding_new_message)
        bottomSheetDialog.show()
        bottomSheetDialog.findViewById<View>(R.id.regbutton)!!.setOnClickListener {
            if (bottomSheetDialog.findViewById<TextView>(R.id.phone)!!.text.toString() != "" &&
                bottomSheetDialog.findViewById<TextView>(R.id.message)!!.text.toString() != ""
            ) {
                SendingPost(this).execute(
                    "http://checkers24.ru/ibook/",
                    CreateJSON.JSON(
                        intent.extras?.get("token").toString(),
                        bottomSheetDialog.findViewById<TextView>(R.id.phone)!!.text.toString(),
                        intent.extras?.get("profile_picture").toString(),
                        bottomSheetDialog.findViewById<TextView>(R.id.message)!!.text.toString()
                    )
                )
                listOfLetters(token)
                bottomSheetDialog.cancel()
            }
        }
    }

    override fun callingBack(s: String?) {
        if (s != null) {
            if (s.split(" ")[0] == "get_list:") {
                if (s.length != 10) takeList(s.substring(10))
            } else {
                Log.e("server", s)
            }
        }
    }

    private fun takeList(senders: String) {
        for (i in senders.split(" ").indices) {
            Log.e("server", senders.split(" ")[i])
            messageView(senders.split(" "))
        }
    }

    private fun messageView(split: List<String>) {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomRecyclerAdapter(split.toList(), this)
    }
}
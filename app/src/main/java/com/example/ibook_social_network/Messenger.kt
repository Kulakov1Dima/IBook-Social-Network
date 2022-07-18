package com.example.ibook_social_network


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.util.*


class Messenger : AppCompatActivity(), SendingPost.Callback {

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
        val url: String = intent.extras?.get("profile_picture").toString()
        if (url != "null") {
            Glide.with(this)
                .load(url)
                .into(findViewById<View>(R.id.image_profile) as CircleImageView)
        }
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
                bottomSheetDialog.cancel()
            }
        }
    }

    override fun callingBack(s: String?) {
        if (s != null) {
            Log.e("server", s)
        }
    }
}
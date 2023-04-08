package com.example.ibook_social_network.loading

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.ibook_social_network.R

class ShowUpdate {
    companion object {

        private const val NOTIFICATION_ID = 107
        private const val CHANNEL_ID = "ibook update"

        @SuppressLint("MissingPermission")
        @JvmStatic
        fun show(version: String, current_version: String, context: Context) {
            if (version != current_version) {

                val updateIntent = Intent(context, UpdateActivity::class.java)
                val snoozePendingIntent = PendingIntent.getActivity(context.applicationContext, 0, updateIntent, PendingIntent.FLAG_IMMUTABLE)

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Обновление")
                    .setContentText("Доступна новая версия Ibook: $version")
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_up, "Обновить", snoozePendingIntent)

                val notificationManager = NotificationManagerCompat.from(context)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, IMPORTANCE_DEFAULT)
                    channel.enableVibration(true)
                    notificationManager.createNotificationChannel(channel)
                }

                notificationManager.notify(NOTIFICATION_ID, builder.build())
            }
        }
    }

}
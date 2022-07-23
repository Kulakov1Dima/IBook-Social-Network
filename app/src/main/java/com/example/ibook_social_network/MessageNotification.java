package com.example.ibook_social_network;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MessageNotification {
    private static final String description= "Оповещение о новых сообщениях";
    private static final CharSequence name = "Сообщения";
    private static final String CHANNEL_ID = "Сообщения";

    static void createNotificationChannel(MessageService messageService) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = messageService.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    static void send_notification(MessageService messageService, String title, String message){
        Intent intent = new Intent(messageService, Messenger.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(messageService.getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(messageService, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setFullScreenIntent(pendingIntent, true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(messageService);
        notificationManager.notify(123, builder.build());
    }
}

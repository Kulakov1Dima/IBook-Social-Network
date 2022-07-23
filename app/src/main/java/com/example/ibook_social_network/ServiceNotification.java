package com.example.ibook_social_network;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ServiceNotification {

    private static final String CHANNEL_ID = "Фоновая работа приложения";
    public static NotificationManager notificationManager;

    public static void sendNotification(String title, String message, MessageService messageService) {
        notificationManager = (NotificationManager) messageService.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(messageService.getApplicationContext(), SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(messageService.getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(messageService.getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(message);
        createChannelIfNeeded(notificationManager, CHANNEL_ID);
        messageService.startForeground(2768, notificationBuilder.build());
    }

    public static void createChannelIfNeeded(NotificationManager manager, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}


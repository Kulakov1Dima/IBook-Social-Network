package com.example.ibook_social_network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AddNotification {
    public static NotificationManager notificationManager;

    public static void sendNotification(String Title, String Text, MessageService messageService, String CHANNEL_ID, int NOTIFY_ID) {
        notificationManager = (NotificationManager) messageService.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(messageService.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(messageService.getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(messageService.getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(Title)
                        .setContentText(Text);
        if(NOTIFY_ID != 655){
            notificationBuilder.setAutoCancel(true);
        }
        createChannelIfNeeded(notificationManager, CHANNEL_ID, NOTIFY_ID);
        Notification notification;
        notification = notificationBuilder.build();
        messageService.startForeground(NOTIFY_ID, notification);
    }

    public static void createChannelIfNeeded(NotificationManager manager, String CHANNEL_ID, int NOTIFY_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(NOTIFY_ID == 655) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(notificationChannel);
            }
            else{
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }



}

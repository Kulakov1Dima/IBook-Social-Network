package com.example.ibook_social_network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class IbookMessengerService extends Service implements SendingPost.Callback {

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager)
                this.getSystemService(NOTIFICATION_SERVICE);
    }

    boolean hand = false;
    private Handler mHandler = new Handler();
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    private NotificationManager notificationManager1;
    private static final int NOTIFY_ID1 = 2;
    private static final String CHANNEL_ID1 = "CHANNEL_IBOOK";

    private Runnable badTimeUpdater = new Runnable() {
        @Override
        public void run() {
            server();
            if (!hand) mHandler.postDelayed(this, 9000);
        }
    };

    public IbookMessengerService() {
        server();
        mHandler.removeCallbacks(badTimeUpdater);
        mHandler.postDelayed(badTimeUpdater, 9000);
    }

    void server() {
        Log.e("IbookService", "startSending");
        new SendingPost(this).execute("getMessage", MainActivity.mSettings.getString("Nickname", ""), "103", "1.0v");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //sendNotification("Ibook Service started", "Ibook Service", "получение сообщений: включено");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        hand = true;
        notificationManager.cancel(NOTIFY_ID);

        //Disabling service
        stopSelf();
        super.onDestroy();
    }

    @Override
    public void callingBack(String s) {
        if (!s.equals("null")) {
            try {
                JSONObject responseObj = new JSONObject(s);
                Log.e("IbookServerOut", String.valueOf(responseObj.get("number")));
                Log.e("IbookServerOut", String.valueOf(responseObj.get("message")));

                notificationManager1 = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID1)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .setContentTitle(String.valueOf(responseObj.get("number")))
                                .setContentText(String.valueOf(responseObj.get("message")))
                                .setAutoCancel(true);
                createChannelIfNeeded1(notificationManager1);
                notificationManager1.notify(NOTIFY_ID1, notificationBuilder.build());

                Message.takeMyMessengers(String.valueOf(responseObj.get("number")).replaceAll(" ", ""), String.valueOf(responseObj.get("message")), this);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public static void createChannelIfNeeded1(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID1, CHANNEL_ID1, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification(String Ticker,String Title,String Text) {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(Title)
                        .setContentText(Text);
        createChannelIfNeeded(notificationManager);
        Notification notification;
        notification = notificationBuilder.build();
        startForeground(NOTIFY_ID, notification);
    }
}
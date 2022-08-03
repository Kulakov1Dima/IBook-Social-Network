package com.example.ibook_social_network;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MessageService extends Service {

    private MessageService.ServiceHandler serviceHandler;
    class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                URL url = new URL("http://checkers24.ru/ibook/"+Configuration.email);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.d("SSE", "http response: " + urlConnection.getResponseCode());
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                SSE.setInputStream(inputStream, Configuration.email, MessageService.this);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("SSE activity", "Error on url openConnection: " + e.getMessage());
                e.printStackTrace();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            handleMessage(msg);
        }
    }

    @Override
    public void onCreate() {

        HandlerThread thread = new HandlerThread("IbookService",Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ServiceNotification.sendNotification("Фоновая работа приложения", "Получение уведомлений: включено", this);
        MessageNotification.createNotificationChannel(this);
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        ServiceNotification.notificationManager.cancel(2768);
        stopSelf();
        super.onDestroy();
    }
}
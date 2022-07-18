package com.example.ibook_social_network;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;

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
                URL url = new URL("http://checkers24.ru/ibook/"+MainActivity.email);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                Log.d("SSE", "http response: " + urlConnection.getResponseCode());
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                SSE.setInputStream(inputStream, MainActivity.email, MessageService.this);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("SSE activity", "Error on url openConnection: " + e.getMessage());
                e.printStackTrace();
                try {
                    Thread.sleep(10000);
                    handleMessage(msg);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
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
        AddNotification.sendNotification("Ibook Service", "получение сообщений: включено", this, "Ibook Service", 655);
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        AddNotification.notificationManager.cancel(655);
        //Disabling service
        stopSelf();
        super.onDestroy();
    }
}
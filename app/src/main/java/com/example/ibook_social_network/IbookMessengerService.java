package com.example.ibook_social_network;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

public class IbookMessengerService extends Service implements SendingPost.Callback {
    boolean hand = false;
    private Handler mHandler = new Handler();

    private Runnable badTimeUpdater = new Runnable() {
        @Override
        public void run() {
            server();
            if(!hand)mHandler.postDelayed(this, 10000);
        }
    };

    public IbookMessengerService() {
        server();
        mHandler.removeCallbacks(badTimeUpdater);
        mHandler.postDelayed(badTimeUpdater, 10000);
    }

    void server(){
        new SendingPost(this).execute("getMessage", MainActivity.mSettings.getString("Nickname", ""), "103", "1.0v");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("IbookServerOut", "старт");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.e("IbookServerOut", "остановка");
        hand = true;
        super.onDestroy();
    }

    @Override
    public void callingBack(String s) {
        if (!s.equals("null")) {
            try {
                JSONObject responseObj = new JSONObject(s);
                Log.e("IbookServerOut", String.valueOf(responseObj.get("number")));
                Log.e("IbookServerOut", String.valueOf(responseObj.get("message")));
                Message.takeMyMessengers(String.valueOf(responseObj.get("number")).replaceAll(" ", ""),String.valueOf(responseObj.get("message")),this);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
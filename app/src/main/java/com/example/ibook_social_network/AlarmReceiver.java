package com.example.ibook_social_network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class AlarmReceiver extends BroadcastReceiver implements SendingPost.Callback
{
    Context context1;
    @Override
    public void onReceive(Context context, Intent intent) {
        context1 = context;
        new SendingPost(this).execute("getMessage", MainActivity.mSettings.getString("Nickname", ""), "103", "1.0v");
    }

    @Override
    public void callingBack(String s) {
        if (!s.equals("null")) {
            try {
                JSONObject responseObj = new JSONObject(s);
                Log.e("IbookServerOut", String.valueOf(responseObj.get("number")));
                Log.e("IbookServerOut", String.valueOf(responseObj.get("message")));

                Message.takeMyMessengers(String.valueOf(responseObj.get("number")).replaceAll(" ", ""),String.valueOf(responseObj.get("message")),context1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.example.ibook_social_network;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class GetMessage {

    @SuppressLint("NotifyDataSetChanged")
    public static void getCommand(String line, String email, MessageService messageService, BufferedReader reader) throws IOException, JSONException {
        try {
            //System.out.println(line);
            if(line.equals("null")){
                reader.close();
                return;
            }
            if(line.charAt(0)=='<'){
                reader.close();
                return;
            }
            JSONObject userJson = new JSONObject(line);
            if(!userJson.isNull("message")){
                MessageService.countConnect = 0;
                AddPost.post("http://ibook.agency/ibook/" + email + "/del.php", line);
                reader.close();
                JSONObject messageInfo = new JSONObject(userJson.getString("message"));
                MessageNotification.send_notification(messageService, messageInfo.getString("token"), messageInfo.getString("message"));
                sendMessage(messageService,"@"+messageInfo.getString("message"));
            }
        }
        catch (NullPointerException e){
            reader.close();
        }
    }

    private static void sendMessage(MessageService messageService, String s) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", s);
        LocalBroadcastManager.getInstance(messageService).sendBroadcast(intent);
    }
}

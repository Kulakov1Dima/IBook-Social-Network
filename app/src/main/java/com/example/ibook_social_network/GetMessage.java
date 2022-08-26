package com.example.ibook_social_network;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetMessage {

    @SuppressLint("NotifyDataSetChanged")
    public static boolean getCommand(String line, String email, MessageService messageService) throws IOException, JSONException {
        System.out.println(line);
        if(!line.equals("")) {
            JSONObject userJson = new JSONObject(line);
            if(!userJson.isNull("message")){
                AddPost.post("http://ibook.agency/ibook/" + email + "/del.php", line);
                JSONObject messageInfo = new JSONObject(userJson.getString("message"));
                MessageNotification.send_notification(messageService, messageInfo.getString("token"), messageInfo.getString("message"));
                sendMessage(messageService,"@"+messageInfo.getString("message"));
            }
        }
        return line.equals("");
    }

    private static void sendMessage(MessageService messageService, String s) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", s);
        LocalBroadcastManager.getInstance(messageService).sendBroadcast(intent);
    }
}

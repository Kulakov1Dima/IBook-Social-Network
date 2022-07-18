package com.example.ibook_social_network;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateJSON {
    public static String JSON(String token, String recipient, String photo, String message){
        JSONObject json = new JSONObject();
        try {
            json.put("token", token);
            json.put("recipient", recipient);
            json.put("photo", photo);
            json.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}

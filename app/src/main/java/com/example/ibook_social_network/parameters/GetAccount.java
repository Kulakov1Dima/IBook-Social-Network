package com.example.ibook_social_network.parameters;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ibook_social_network.authorization.AuthActivity;

public class GetAccount {
    public static final String APP_PREFERENCES = "ibookSettings";

    public static String getEmail(AuthActivity authActivity) {
        SharedPreferences accountSettings = authActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (accountSettings.contains("google")) {
            return (accountSettings.getString("google", ""));
        }
        if (accountSettings.contains("yandex")) {
            return (accountSettings.getString("yandex", ""));
        }
        return null;
    }
}

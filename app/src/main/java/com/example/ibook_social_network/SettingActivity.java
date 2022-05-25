package com.example.ibook_social_network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "accountSettings";
    public static final String APP_PREFERENCES_URL = "server_settings";
    public static SharedPreferences mSettings;
    TextView address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Config graphicsConfiguration = new Config();
        graphicsConfiguration.setStatusBarColor(getWindow());
        super.onCreate(savedInstanceState);
        Config NetworkConfiguration = new Config();
        setContentView(R.layout.activity_setting);
        address = findViewById(R.id.address);
        address.setText(NetworkConfiguration.url);
    }
    public void addAddress(View view) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_URL, address.getText().toString());
        editor.apply();
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
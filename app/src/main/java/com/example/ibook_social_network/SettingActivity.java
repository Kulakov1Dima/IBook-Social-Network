package com.example.ibook_social_network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Config graphicsConfiguration = new Config();
        graphicsConfiguration.setStatusBarColor(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
package com.example.ibook_social_network;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Config graphicsConfiguration = new Config();
        graphicsConfiguration.setStatusBarColor(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        RecyclerView settings = (RecyclerView)findViewById(R.id.settings);
    }
}
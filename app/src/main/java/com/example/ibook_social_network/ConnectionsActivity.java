package com.example.ibook_social_network;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ConnectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);
        Configuration.awp(getWindow(), Objects.requireNonNull(getSupportActionBar()));
    }

    public void settingsWifi(View view) {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            wifiManager.setWifiEnabled(true);
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        } else {
            ActivityResultLauncher.launch(new Intent(Settings.Panel.ACTION_WIFI));
        }
    }

    public void settingsMobileData(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        else ActivityResultLauncher.launch(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
    }

    public void connected(View view) {
        finish();
        startActivity(new Intent(this, SplashScreen.class));
    }

    ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                finish();
                startActivity(new Intent(this, SplashScreen.class));
            });
}
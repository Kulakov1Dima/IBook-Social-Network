package com.example.ibook_social_network;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingActivity extends AppCompatActivity {
    private RecyclerView numbersList;
    private NumbersAdapter numbersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Config graphicsConfiguration = new Config();
        graphicsConfiguration.setStatusBarColor(getWindow());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        numbersList = (RecyclerView)findViewById(R.id.settings);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        numbersList.setLayoutManager(layoutManager);

        numbersList.setHasFixedSize(true);

        numbersAdapter = new NumbersAdapter(100);
        numbersList.setAdapter(numbersAdapter);
    }
}
package com.example.IBook;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
/*
Экран профиля показаны активность (в минутах), рекомендации и полезные ссылки
Во варианте закрытого софта: сведения о тарифе pro версии (но наврятли так будет :) )
 */
public class ProfileActivity extends AppCompatActivity {
    SharedPreferences pageL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pageL = getSharedPreferences(Сonfiguration.CURRENT_PAGE, Context.MODE_PRIVATE);
        customizationActionBar();
        ImageButton registration= findViewById(R.id.imageButton2);
        registration.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PageReadActivity.class);
            startActivity(intent);
        });

    }
    void customizationActionBar(){
        getSupportActionBar().setTitle("Профиль");                                   //название страницы
    }}
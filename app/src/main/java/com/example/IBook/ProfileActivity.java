package com.example.IBook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences pageL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Профиль");                                                  //название страницы
        pageL = getSharedPreferences(Сonfiguration.CURRENT_PAGE, Context.MODE_PRIVATE);
        ImageButton registration = findViewById(R.id.imageButton2);
        registration.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PageReadActivity.class);
            startActivity(intent);
        });

    }
}


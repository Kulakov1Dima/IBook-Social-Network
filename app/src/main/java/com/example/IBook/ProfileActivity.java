package com.example.IBook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Профиль");                                                  //название страницы
        ImageButton registration = findViewById(R.id.imageButton2);
        registration.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PageReadActivity.class);
            startActivity(intent);
        });
    }
}


package com.example.IBook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PasswdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwd);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();                                                   //удаление загаловка
        nextActivity();
    }

    void nextActivity() {
        ImageButton registration = findViewById(R.id.imageButton);
        registration.setOnClickListener(v -> {
            Intent intent = new Intent(PasswdActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}

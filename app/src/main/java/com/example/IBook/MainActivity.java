package com.example.IBook;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/*
Ibook - приложение соцсети.
21.10.2021  01:09 Кулаков Дмитрий
 */

public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        delActionBar();                                                 //удаление загаловка
        nextActivity();
    }

    void delActionBar(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    String getPhone(){
        TextView number = findViewById(R.id.phone);
        return number.getText().toString();
    }

    void nextActivity(){
        ImageButton registration= findViewById(R.id.imageButton);
        registration.setOnClickListener(v -> {
            new SendingPost().execute("authorization",getPhone());
        });
    }
}
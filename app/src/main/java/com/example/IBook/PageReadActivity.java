package com.example.IBook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class PageReadActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read);
        getSupportActionBar().setTitle("Сообщения");                                                  //название страницы

    }
}
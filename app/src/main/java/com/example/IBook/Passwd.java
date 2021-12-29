package com.example.IBook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Passwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwd);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();                                                   //удаление загаловка
    }
}
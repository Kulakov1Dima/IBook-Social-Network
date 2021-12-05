package com.example.IBook;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/*
Уголовный кодекс тестовые задания -приложение которое поможет вам узнать лучше законадательные акты Российской Федерации
21.10.2021  01:09 Кулаков Дмитрий
 */
public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iBook();
        //buttons();                                                                                  //все кнопки главного Activity
    }

void iBook(){
    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
    startActivity(intent);
}}
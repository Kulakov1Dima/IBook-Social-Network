package com.example.criminalcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
/*
Уголовный кодекс тестовые задания -приложение которое поможет вам узнать лучше законадательные акты Российской Федерации
21.10.2021  01:09
 */

public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Уголовный кодекс 2021");    //название
        ImageButton profileButton = (ImageButton) findViewById(R.id.profileButton); //кнопка профиля
        profileButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,profileActivity.class);
                startActivity(intent);//переход на экран профиля
            }
        });
        ImageButton readButton = (ImageButton) findViewById(R.id.readButton);   //кнопка чтения
        readButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,readActivity.class);
                startActivity(intent);  //переход на экран со статьями уголовного кодекса
            }
        });
        //тут должны быть ещё две кнопки
        //тестовые задания
        //ошибки(мой прогресс)

    }
}
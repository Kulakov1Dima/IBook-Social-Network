package com.example.criminalcode;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
/*
Уголовный кодекс тестовые задания -приложение которое поможет вам узнать лучше законадательные акты Российской Федерации
21.10.2021  01:09 Кулаков Дмитрий
 */
public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons();                                                                                  //все кнопки главного Activity
    }

public void buttons(){
    ImageButton profileButton = findViewById(R.id.profileButton);                                   //кнопка профиля
    profileButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this,profileActivity.class);
        startActivity(intent);                                                                      //переход на экран профиля
    });
    ImageButton readButton = findViewById(R.id.readButton);                                         //кнопка чтения статей
    readButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this,readActivity.class);
        startActivity(intent);                                                                      //переход на экран со статьями уголовного кодекса
    });
    }
}
package com.example.criminalcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
Экран профиля показаны активность(в минутах), рекомендации и полезные ссылки
Во врианте закрытого софта: сведения о тарифе pro версии (но наврятли так будет :) )
 */
public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Привет user!");    //название
        //кнопка вернуться на главную
        //посещаемость
        //решено задач
        //мои цели
        //последняя страничка в теории (возращает пользователя на страничку чтения где он был последний раз)
        //рекомендации
        //отключить уведомления
        //преобрести pro версию
    }
}
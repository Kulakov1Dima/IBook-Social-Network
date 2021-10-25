package com.example.criminalcode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

/*
Экран профиля показаны активность(в минутах), рекомендации и полезные ссылки
Во врианте закрытого софта: сведения о тарифе pro версии (но наврятли так будет :) )
 */
public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад
        getSupportActionBar().setTitle("Добро пожаловать user!");     //название страницы

        //посещаемость
        //решено задач
        //мои цели
        //последняя страничка в теории (возращает пользователя на страничку чтения где он был последний раз)
        //рекомендации
        //отключить уведомления
        //преобрести pro версию
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
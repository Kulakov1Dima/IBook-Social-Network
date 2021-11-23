package com.example.criminalcode;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
/*
Экран профиля показаны активность (в минутах), рекомендации и полезные ссылки
Во варианте закрытого софта: сведения о тарифе pro версии (но наврятли так будет :) )
 */
public class profileActivity extends AppCompatActivity {
    SharedPreferences pageL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pageL = getSharedPreferences("NpageL", Context.MODE_PRIVATE);
        customizationActionBar();
        textRead();                                                                                 //функция прогрессбара и вывода номера последней страницы в профиле
        buttons();                                                                                  //все кнопки на активности профиля
    }
    void customizationActionBar(){
        ActionBar actionBar = getSupportActionBar();                                                //получение доступа к ActionBar
        assert actionBar != null;                                                                   //исключение исключения
        actionBar.setHomeButtonEnabled(true);                                                       //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);                                                  //отображение кнопки назад
        getSupportActionBar().setTitle("Добро пожаловать user!");                                   //название страницы
    }
    void buttons(){
        Button lastPage = findViewById(R.id.button2);
        lastPage.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this,pageRead.class);
            intent.putExtra("Npage",pageL.getInt("NpageL",0));
            startActivity(intent);                      //переход на экран чтения статей(pdf файл)
        });
    }
    void textRead(){                                        //функция прогрессбара и вывода номера последней страницы в профиле
        TextView pageN = findViewById(R.id.textView3);      //страницы в прогрессе чтения
        String pageСounter = (pageL.getInt("NpageL",0) + 1) +"/"+ pageL.getInt("pageC",0);
        pageN.setText(pageСounter);

        double maxProgress=pageL.getInt("pageC",0)/100; //переменная равная 1% от всех страниц

        ProgressBar readPage = findViewById(R.id.progressBar2);
        readPage.setProgress((int)((pageL.getInt("NpageL",0) + 1)/maxProgress)); //процент прочитанного

        readPage.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this,pageRead.class);
            intent.putExtra("Npage",pageL.getInt("NpageL",0));
            startActivity(intent);                      //переход на экран чтения статей(pdf файл)
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
}
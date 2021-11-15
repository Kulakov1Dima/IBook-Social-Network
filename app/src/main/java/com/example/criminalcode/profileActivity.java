package com.example.criminalcode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
Экран профиля показаны активность(в минутах), рекомендации и полезные ссылки
Во врианте закрытого софта: сведения о тарифе pro версии (но наврятли так будет :) )
 */
public class profileActivity extends AppCompatActivity {

    private static SharedPreferences pageL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pageL = getSharedPreferences("NpageL", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад
        getSupportActionBar().setTitle("Добро пожаловать user!");     //название страницы

        textRead();

        Button lastPage = (Button) findViewById(R.id.button2);
        lastPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this,pageRead.class);
                intent.putExtra("Npage",pageL.getInt("NpageL",0));
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });
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
    void textRead(){
        TextView pageN = findViewById(R.id.textView3);      //страницы в прогрессе чтения
        pageN.setText((int)(pageL.getInt("NpageL",0) + 1)+"/"+ pageL.getInt("pageC",0));

        double maxProgress=pageL.getInt("pageC",0)/100;

        ProgressBar readPage =(ProgressBar)findViewById(R.id.progressBar2);
        readPage.setProgress((int)((pageL.getInt("NpageL",0) + 1)/maxProgress));

        readPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this,pageRead.class);
                intent.putExtra("Npage",pageL.getInt("NpageL",0));
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

    }
}
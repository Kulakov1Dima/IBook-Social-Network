package com.example.criminalcode;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//экран чтения-теоретический материал
// по моим предположениям в приложении должны открываться PDF файлы

public class readActivity extends AppCompatActivity {

    private static SharedPreferences pageL;
    public static int lastP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getSupportActionBar().setTitle("Статьи уголовного кодекса");
        //глава I и тд.
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад

        pageL = getSharedPreferences("NpageL", Context.MODE_PRIVATE);
        lastP=pageL.getInt("NpageL",0);
        if(lastP>1) {
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "custom");
        }


        Button chapter1 = (Button) findViewById(R.id.chapter1);
        chapter1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",0);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });
        Button chapter2 = (Button) findViewById(R.id.chapter2);
        chapter2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",2);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter3 = (Button) findViewById(R.id.chapter3);
        chapter3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",11);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter4 = (Button) findViewById(R.id.chapter4);
        chapter4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",28);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter5 = (Button) findViewById(R.id.chapter5);
        chapter5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",36);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter6 = (Button) findViewById(R.id.chapter6);
        chapter6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",39);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter7 = (Button) findViewById(R.id.chapter7);
        chapter7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",45);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter8 = (Button) findViewById(R.id.chapter8);
        chapter8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",76);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter9 = (Button) findViewById(R.id.chapter9);
        chapter9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",131);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter10 = (Button) findViewById(R.id.chapter10);
        chapter10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",192);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter11 = (Button) findViewById(R.id.chapter11);
        chapter11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",227);
                startActivity(intent);                      //переход на экран чтения статей(pdf файл)
            }
        });

        Button chapter12 = (Button) findViewById(R.id.chapter12);
        chapter12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
                intent.putExtra("Npage",233);
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
}
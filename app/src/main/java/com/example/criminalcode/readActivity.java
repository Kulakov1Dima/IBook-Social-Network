package com.example.criminalcode;


import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        getSupportActionBar().setTitle("Статьи уголовного кодекса");
        //глава I и тд.
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад

        Button chapter1 = (Button) findViewById(R.id.chapter1);
        chapter1.setOnClickListener(new View.OnClickListener(){//временно висит pageRead на первой кнопке, чтобы понять как вообще делать
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(readActivity.this,pageRead.class);
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
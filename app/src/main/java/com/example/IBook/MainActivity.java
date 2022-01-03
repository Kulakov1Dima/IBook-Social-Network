package com.example.IBook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Ibook - приложение соцсети.
 * 21.10.2021  01:09 Кулаков Дмитрий
 */

public class MainActivity extends AppCompatActivity implements SendingPost.Callback {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        delActionBar();                                                 //удаление загаловка
        serverPolling();                                                //опрос сервера во втором потоке
    }

    void delActionBar(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    String getPhone(){
        TextView number = findViewById(R.id.phone);
        return number.getText().toString();
    }

    void serverPolling(){
        findViewById(R.id.imageButton).setOnClickListener(v -> new SendingPost(this).execute("authorization",getPhone()));
    }

    public void callingBack(String dataResponse){
        if(String.valueOf(dataResponse).equals("{\"command\":\"authorization\",\"registration\":false}")){
            dialogRegistration();
        }
    }

    void dialogRegistration(){
        AlertDialog.Builder registration = new AlertDialog.Builder(MainActivity.this);
        registration.setMessage("Вы не зарегестрированны. Хотите это исправить?")
                .setCancelable(false)
                .setPositiveButton("Да", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(MainActivity.this, PasswdActivity.class);
                    intent.putExtra("phone", getPhone());
                    startActivity(intent);
                })
                .setNegativeButton("Нет", (dialog, which) -> finish());
        AlertDialog alert = registration.create();
        alert.setTitle("Регистрация");
        alert.show();
    }
}
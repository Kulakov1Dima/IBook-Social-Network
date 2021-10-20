package com.example.criminalcode;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//экран чтения-теоретический материал
// по моим предположениям в приложении должны открываться PDF файлы

public class readActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        //глава I и тд.
    }
}

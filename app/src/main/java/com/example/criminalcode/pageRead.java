package com.example.criminalcode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

public class pageRead extends AppCompatActivity implements OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView
                .fromAsset("ukodeksrf.pdf")
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .load();
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        setTitle(String.format("%s %s / %s","Уголовный кодекс: ", page + 1, pageCount));
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
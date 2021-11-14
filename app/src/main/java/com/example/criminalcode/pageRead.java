package com.example.criminalcode;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;


public class pageRead extends AppCompatActivity implements OnPageChangeListener {

    private static SharedPreferences pageL;
    private static SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read);

        pageL = getSharedPreferences("NpageL", Context.MODE_PRIVATE);
        editor = pageL.edit();

        int nPage=0;
        nPage= getIntent().getIntExtra("Npage",0);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView
                .fromAsset("ukodeksrf.pdf")
                .defaultPage(nPage)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .load();
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        setTitle(String.format("Уголовный кодекс"));
        TextView pageN = findViewById(R.id.textView11);
        pageN.setText((int)(page + 1)+"/"+ pageCount);

        editor.putInt("NpageL",page).apply();
        editor.putInt("pageC",pageCount).apply();
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
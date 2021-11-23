package com.example.criminalcode;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;


public class pageRead extends AppCompatActivity implements OnPageChangeListener {

    private static SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read);

        SharedPreferences putPageNumber = getSharedPreferences("NpageL", Context.MODE_PRIVATE);
        editor = putPageNumber.edit();

        int pageNumber;
        pageNumber= getIntent().getIntExtra("Npage",0);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView
                .fromAsset("ukodeksrf.pdf")
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .load();
        ActionBar actionBar = getSupportActionBar();        //получение доступа к ActionBar
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);               //включение кнопки назад
        actionBar.setDisplayHomeAsUpEnabled(true);          //отображение кнопки назад

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageChanged(int page, int pageCount) {
        setTitle("Уголовный кодекс");
        TextView pageN = findViewById(R.id.textView11);
        pageN.setText((int)(page + 1)+"/"+ pageCount);

        editor.putInt("NpageL",page).apply();
        editor.putInt("pageC",pageCount).apply();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(pageRead.this, MainActivity.class);
            startActivity(intent);  //переход на главную страницу
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
}
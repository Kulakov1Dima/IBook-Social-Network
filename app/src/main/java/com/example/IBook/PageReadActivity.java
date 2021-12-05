package com.example.IBook;
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


public class PageReadActivity extends AppCompatActivity implements OnPageChangeListener {

    SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read);

        SharedPreferences putPageNumber = getSharedPreferences(Сonfiguration.CURRENT_PAGE, Context.MODE_PRIVATE);
        editor = putPageNumber.edit();

        int pageNumber;
        pageNumber= getIntent().getIntExtra(Сonfiguration.OLD_CURRENT_PAGE,0);

        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView
                .fromAsset(Сonfiguration.pdfDocument)
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
        setTitle(R.string.ActionBarTitleName);
        TextView pageN = findViewById(R.id.textView11);
        pageN.setText((page + 1) +"/"+ pageCount);

        editor.putInt(Сonfiguration.CURRENT_PAGE,page).putInt(Сonfiguration.numberOfPages,pageCount).apply();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(PageReadActivity.this, MainActivity.class);
            startActivity(intent);  //переход на главную страницу
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
}
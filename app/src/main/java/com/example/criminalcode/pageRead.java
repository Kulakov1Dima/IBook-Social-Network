package com.example.criminalcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class pageRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_read);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("P.pdf").load();

    }
}
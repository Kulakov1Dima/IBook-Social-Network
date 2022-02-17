package com.example.ibook_social_network;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;


public class MessengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createToolBar();
    }

    void createToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onWifiContactsAction(MenuItem wifi){
        Intent intent = new Intent(MessengerActivity.this, WifiFriendsActivity.class);
        startActivity(intent);
    }

    public void onNewMessageAction(MenuItem message){
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.add_first_messenge);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
        LinearLayout upload = bottomSheetDialog.findViewById(R.id.phoneLinearLayout);
        LinearLayout download = bottomSheetDialog.findViewById(R.id.textmessenge);
        LinearLayout delete = bottomSheetDialog.findViewById(R.id.messenge);
        bottomSheetDialog.show();
    }
}

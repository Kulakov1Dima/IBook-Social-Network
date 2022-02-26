package com.example.ibook_social_network;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class MessengerActivity extends AppCompatActivity implements SendingPost.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        setStatusBarColor("#C94E4949");

    }

    public void onNewMessageAction(View view){
        showBottomSheetDialog();
    }

    String getPhone(BottomSheetDialog bottomSheetDialog){
        TextView number =  bottomSheetDialog.findViewById(R.id.phone);
        return Objects.requireNonNull(number).getText().toString();
    }

    String getMessage(BottomSheetDialog bottomSheetDialog){
        TextView password =  bottomSheetDialog.findViewById(R.id.editTextTextPersonName);
        return Objects.requireNonNull(password).getText().toString();
    }


    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.add_first_messenge);
        bottomSheetDialog.show();
        Bundle arguments = getIntent().getExtras();
        bottomSheetDialog.findViewById(R.id.regbutton).setOnClickListener(v-> new SendingPost(this).execute(" +7"+arguments.get("myPhone").toString(), getPhone(bottomSheetDialog).replaceAll("\\+7",""), getMessage(bottomSheetDialog), "0.1v"));
    }



    public void setStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            int statusBarColor = Color.parseColor(color);
            if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            window.setStatusBarColor(statusBarColor);
        }
    }
    @Override
    public void callingBack(String dataResponse) {
        Config ErrorToastConfiguration = new Config();
        if(dataResponse.equals("ok"))saveMyMessengers();
        else Toast.makeText(getApplicationContext(),
                ErrorToastConfiguration.errorServer,
                Toast.LENGTH_SHORT).show();
    }

    void saveMyMessengers(){
        Log.e("IbookServerMessage","write file");
    }

}
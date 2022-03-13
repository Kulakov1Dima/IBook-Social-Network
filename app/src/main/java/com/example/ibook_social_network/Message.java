package com.example.ibook_social_network;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Message extends AppCompatActivity implements SendingPost.Callback {

    private String textMessage;

    private Runnable badTimeUpdater = new Runnable() {
        @Override
        public void run() {
            viewMessages(getIntent().getExtras().get("phone").toString());
            mHandler.postDelayed(this, 5000);
        }
    };

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenge);
        setStatusBarColor("#000000");
        TextView textView = findViewById(R.id.textView3);
        textView.setText(getIntent().getExtras().get("phone").toString());
        viewMessages(getIntent().getExtras().get("phone").toString());
        findViewById(R.id.button2).setOnClickListener(v -> {
            stopService(new Intent(this, IbookMessengerService.class));
            new SendingPost(this).execute(" +7" + getIntent().getExtras().get("myMessage").toString(), getIntent().getExtras().get("phone").toString().replaceAll("\\+7", ""), getMessage(), "0.1v");
            startService(new Intent(this, IbookMessengerService.class));
        });
        mHandler.removeCallbacks(badTimeUpdater);
        mHandler.postDelayed(badTimeUpdater, 1000);
    }



    public void setStatusBarColor(String color) {
        Window window = getWindow();
        int statusBarColor = Color.parseColor(color);
        if (statusBarColor == Color.BLACK && window.getNavigationBarColor() == Color.BLACK) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        window.setStatusBarColor(statusBarColor);
    }

    void viewMessages(String file) {
        FileInputStream fileMessage = null;
        try {
            fileMessage = openFileInput(file);
            byte[] bytes = new byte[fileMessage.available()];
            fileMessage.read(bytes);
            String text = new String(bytes);
            String[] fileMessages = text.split("\n");
            ListView devisesList = findViewById(R.id.messagesList);
            ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_messange, fileMessages);
            devisesList.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (fileMessage != null)
                    fileMessage.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    String getMessage() {
        TextView password = findViewById(R.id.editTextTextPersonName2);
        textMessage =Objects.requireNonNull(password).getText().toString();
        password.setText("");
        return textMessage;
    }

    @Override
    public void callingBack(String dataResponse) {
        Config ErrorToastConfiguration = new Config();
        if (dataResponse.equals("ok")) saveMyMessengers();
        else Toast.makeText(getApplicationContext(),
                ErrorToastConfiguration.errorServer,
                Toast.LENGTH_SHORT).show();
    }
    void saveMyMessengers() {
        Log.e("IbookServerMessage", "write file");
        FileOutputStream fileMessage = null;
        String file = getIntent().getExtras().get("phone").toString();
        try {
            fileMessage = openFileOutput(file, MODE_APPEND);
            textMessage += "\n";
            fileMessage.write(textMessage.getBytes());
            Log.e("IbookServerMessage", "saved file");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileMessage != null)
                    fileMessage.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        viewMessages(getIntent().getExtras().get("phone").toString());
    }
    static void takeMyMessengers(String file, String textMessage, Context context) {
        Log.e("IbookServerMessage", "write file");
        FileOutputStream fileMessage = null;
        try {
            fileMessage = context.openFileOutput(file, MODE_APPEND);
            textMessage += "\n";
            fileMessage.write(textMessage.getBytes());
            Log.e("IbookServerMessage", "saved file");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileMessage != null)
                    fileMessage.close();
            } catch (IOException ex) {
            }
        }
    }
}
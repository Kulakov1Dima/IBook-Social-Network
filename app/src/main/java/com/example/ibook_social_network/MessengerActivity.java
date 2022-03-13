package com.example.ibook_social_network;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MessengerActivity extends AppCompatActivity implements SendingPost.Callback {

    String getNumber = "";
    String textMessage = "";

    /*Применение цвета к панели навигации*/
    public void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
    }

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        viewMessages();
        mHandler.removeCallbacks(badTimeUpdater);
        mHandler.postDelayed(badTimeUpdater, 10000);
    }

    private Runnable badTimeUpdater = new Runnable() {
        @Override
        public void run() {
            viewMessages();
            mHandler.postDelayed(this, 5000);
        }
    };

    /*просмотр сообщений*/
    void viewMessages() {
        String[] fileMessages = fileList();
        ListView devisesList = findViewById(R.id.messagesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_messange, fileMessages);
        devisesList.setAdapter(adapter);
        devisesList.setOnItemClickListener((parent, itemClicked, position, id) -> openDialogMessage(itemClicked));
    }
    /*открытие диалога*/
    void openDialogMessage(View itemClicked){
        TextView textView = (TextView) itemClicked;
        Intent intent = new Intent(MessengerActivity.this, Message.class);
        intent.putExtra("phone", textView.getText().toString());
        intent.putExtra("myMessage", getIntent().getExtras().get("myPhone").toString());
        startActivity(intent);
    }

    public void onNewMessageAction(View view) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.add_first_messenge);
        bottomSheetDialog.show();
        bottomSheetDialog.findViewById(R.id.regbutton).setOnClickListener(v -> {
            stopService(new Intent(this, IbookMessengerService.class));
            new SendingPost(this).execute(" +7" + getIntent().getExtras().get("myPhone").toString(), getPhone(bottomSheetDialog).replaceAll("\\+7", ""), getMessage(bottomSheetDialog), "0.1v");
            bottomSheetDialog.cancel();
        });
    }

    String getPhone(BottomSheetDialog bottomSheetDialog) {
        TextView number = bottomSheetDialog.findViewById(R.id.phone);
        getNumber = Objects.requireNonNull(number).getText().toString();
        return getNumber;
    }

    String getMessage(BottomSheetDialog bottomSheetDialog) {
        TextView password = bottomSheetDialog.findViewById(R.id.editTextTextPersonName);
        textMessage = Objects.requireNonNull(password).getText().toString();
        return textMessage;
    }

    @Override
    public void callingBack(String dataResponse) {
        Config ErrorToastConfiguration = new Config();
        if (dataResponse.equals("ok")) saveMyMessengers();
        else Toast.makeText(getApplicationContext(),
                ErrorToastConfiguration.errorServer,
                Toast.LENGTH_SHORT).show();
        startService(new Intent(this, IbookMessengerService.class));
    }

    void saveMyMessengers() {
        Log.e("IbookServerMessage", "write file");
        FileOutputStream fileMessage = null;
        String file = getNumber;
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
        Intent intent = new Intent(MessengerActivity.this, Message.class);
        intent.putExtra("phone", getNumber);
        intent.putExtra("myMessage", getIntent().getExtras().get("myPhone").toString());
        startActivity(intent);
        viewMessages();
    }

}
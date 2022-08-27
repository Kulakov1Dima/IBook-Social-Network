package com.example.ibook_social_network;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Dialogue extends AppCompatActivity implements SendingPost.Callback {

    public static RecyclerMessageAdapter recyclerMessageAdapter;
    private static RecyclerView messengerList;
    private String toSendNumber;


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("custom-event-name"));
        super.onResume();
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Dialogue.recyclerMessageAdapter.refreshData(message);
            messengerList.smoothScrollToPosition(recyclerMessageAdapter.getItemCount());
            recyclerMessageAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_dialogue);
        getListMessengers(Configuration.email + "/" + toolBarNumber());
    }

    public String toolBarNumber() {
        toSendNumber = getIntent().getExtras().get("recipient").toString();
        TextView toolBarNumber = findViewById(R.id.toolbarNumber);
        toolBarNumber.setText(toSendNumber);
        return toSendNumber;
    }

    public void getListMessengers(String file) {
        new SendingPost(this).execute("http://ibook.agency/getmessages.php", CreateJSON.JSON(file, null, null, "10"));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void callingBack(String s) {
        Log.e("server", s);
        if (!s.split("\n")[0].equals("<br />") )if (!s.equals("null")) {
            ArrayList<String> textMessage;
            String[] text;
            text = s.split("\n");
            textMessage = new ArrayList<>(Arrays.asList(text));
            updateList(textMessage);
        }
    }

    String getMyText() {
        TextView my_message = findViewById(R.id.myMessage);
        String text = my_message.getText().toString().replace("\n", "")+"                   ";
        my_message.setText("");
        return text;
    }

    public void updateList(ArrayList<String> text) {
        messengerList = findViewById(R.id.messengerView);
        messengerList.setLayoutManager(new LinearLayoutManager(this));
        recyclerMessageAdapter = new RecyclerMessageAdapter(text);
        messengerList.setAdapter(recyclerMessageAdapter);
        messengerList.smoothScrollToPosition(recyclerMessageAdapter.getItemCount());

        Button button = findViewById(R.id.sendButton);
        button.setOnClickListener(v -> {
            String textM = getMyText();
            if (textM.replace(" ", "").length() > 0) {
                saveMessage(textM);
                sendMyMessage(textM);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    static void saveMessage(String myMessage) {
        recyclerMessageAdapter.refreshData(myMessage);
        messengerList.smoothScrollToPosition(recyclerMessageAdapter.getItemCount());
        recyclerMessageAdapter.notifyDataSetChanged();
    }

    void sendMyMessage(String myMessage) {
        new SendingPost(this).execute("http://checkers24.ru/ibook/", CreateJSON.JSON(Configuration.email, toSendNumber, null, myMessage));
    }


}
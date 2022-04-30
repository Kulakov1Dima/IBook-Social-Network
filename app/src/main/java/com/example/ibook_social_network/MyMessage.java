package com.example.ibook_social_network;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyMessage extends AppCompatActivity implements SendingPost.Callback {

    private static String sendNumber;
    private static RecyclerMessageAdapter recyclerMessageAdapter;
    private static RecyclerView messengerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        setStatusBarColor("#000000");
        sendNumber = toolBarNumber();
        getListMessengers(sendNumber);
    }

    public void setStatusBarColor(String color) {
        Window window = getWindow();
        int statusBarColor = Color.parseColor(color);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusBarColor);
    }

    public String toolBarNumber(){
        String toSendNumber = getIntent().getExtras().get("phone").toString();
        TextView toolBarNumber = findViewById(R.id.toolbarNumber);
        toolBarNumber.setText(toSendNumber);
        return toSendNumber;
    }

    public void getListMessengers(String file){
        ArrayList<String> textMessage;
        String[] text;
        FileInputStream fileMessage;
        try {
            fileMessage = openFileInput(file);
            byte[] bytes = new byte[fileMessage.available()];
            fileMessage.read(bytes);
            text = new String(bytes).split("\n");
            textMessage = new ArrayList( Arrays.asList(text));
            updateList(textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateList(ArrayList <String> text){
        messengerList = findViewById(R.id.messengerView);
        messengerList.setLayoutManager(new LinearLayoutManager(this));
        recyclerMessageAdapter = new RecyclerMessageAdapter(text);
        messengerList.setAdapter(recyclerMessageAdapter);
        messengerList.smoothScrollToPosition(recyclerMessageAdapter.getItemCount());

            Button button = findViewById(R.id.sendButton);
            button.setOnClickListener(v -> {
                String textM = getMyText();
                if(textM.length()!=0) {
                    saveMessage(textM,this);
                    sendMyMessage(textM);
                }
                });
    }

    String getMyText(){
        TextView my_message = findViewById(R.id.myMessage);
        String text = my_message.getText().toString();
        my_message.setText("");
        return text;
    }

    static void saveMessage(String myMessage,Context context){
        recyclerMessageAdapter.refreshData(myMessage);
        messengerList.smoothScrollToPosition(recyclerMessageAdapter.getItemCount());

        FileOutputStream fileMessage = null;
        try {
            fileMessage = context.openFileOutput(sendNumber, MODE_APPEND);
            String textMessage = myMessage+"\n";
            fileMessage.write(textMessage.getBytes());
            Log.e("IbookServerMessage", "saved file: "+ textMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileMessage != null)
                    fileMessage.close();
            } catch (IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    void sendMyMessage(String myMessage){
        new SendingPost(this).execute(" +7" + getIntent().getExtras().get("myMessage").toString(), getIntent().getExtras().get("phone").toString().replaceAll("\\+7", ""), myMessage, "0.1v");
    }

    @Override
    public void callingBack(String dataResponse) {
        Config ErrorToastConfiguration = new Config();
        if (dataResponse.equals("ok")) Log.e("Server","ok");
        else Toast.makeText(getApplicationContext(),
                ErrorToastConfiguration.errorServer,
                Toast.LENGTH_SHORT).show();
    }

    static void takeMyMessengers(String file, String textMessage, Context context) {
        textMessage = "@"+textMessage;
        if(file.equals(sendNumber)){
            saveMessage(textMessage, context);
        }
        else {
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
}
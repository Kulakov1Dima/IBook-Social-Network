package com.example.ibook_social_network;

import android.annotation.SuppressLint;
import android.content.Intent;

import java.io.IOException;

public class GetMessage {

    @SuppressLint("NotifyDataSetChanged")
    public static boolean getCommand(String line, String email, MessageService messageService) throws IOException {
        System.out.println(line);
        String endDelete = "";
        if (line.contains("new message")) {
            String message = line.substring(12);
            endDelete = AddPost.post("http://checkers24.ru/ibook/" + email + "/read.php", CreateJSON.JSON(email, null, null, message));
            System.out.println(endDelete);
            String messageM = "";
            if (!endDelete.equals("no file")) {
                MessageNotification.send_notification(messageService, message.split(" ")[0], endDelete);
                messageM = endDelete;
            }
            endDelete = AddPost.post("http://checkers24.ru/ibook/index.php", CreateJSON.JSON(email, "del", null, message));
            System.out.println(endDelete);
            if(!messageM.equals("")){
               Messenger.update = true;
                Dialogue.endDelete = "@"+messageM;
                Dialogue.update = true;
            }

        }
        return endDelete.equals("no file");
    }
}

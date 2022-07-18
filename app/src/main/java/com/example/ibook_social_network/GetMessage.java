package com.example.ibook_social_network;

import java.io.IOException;

public class GetMessage {

    public static boolean getCommand(String line, String email, MessageService messageService) throws IOException {
        System.out.println(line);
        String endDelete = "";
        if (line.contains("new message")) {
            String message = line.substring(12);
            endDelete = AddPost.post("http://checkers24.ru/ibook/"+email+"/read.php", CreateJSON.JSON(email, null, null, message));
            System.out.println(endDelete);
            if(!endDelete.equals("no file"))AddNotification.sendNotification(message.split(" ")[0], endDelete, messageService, message.split(" ")[0],234);
            endDelete = AddPost.post("http://checkers24.ru/ibook/index.php", CreateJSON.JSON(email, "del", null, message));
            System.out.println(endDelete);
        }
        return endDelete.equals("no file");
    }
}

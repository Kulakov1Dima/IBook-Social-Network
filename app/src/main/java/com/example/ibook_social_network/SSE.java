package com.example.ibook_social_network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SSE {

    private static String checkLine = "";

    static void setInputStream(InputStream inputStream, String email, MessageService messageService) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        boolean exit = true;
        while (exit) {
            try {
                String line;
                line = reader.readLine();
                exit = !(line == null);
                if(!checkLine.equals(line)){
                    if(GetMessage.getCommand(line, email, messageService))reader.close();
                    checkLine = line;
                }
            }
            catch (NullPointerException e){
                reader.close();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

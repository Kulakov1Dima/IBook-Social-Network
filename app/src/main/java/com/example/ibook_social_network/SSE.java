package com.example.ibook_social_network;

import org.json.JSONException;

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
                try {
                    String line = reader.readLine();
                    if (!checkLine.equals(line)) {
                        checkLine = line;
                        GetMessage.getCommand(line, email, messageService, reader);
                    }
                    exit = !(line == null);
                } catch (JSONException ignored) {
                }
            } catch (IOException | NullPointerException e) {
                exit = false;
                reader.close();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
           // e.printStackTrace();
        }
    }
}

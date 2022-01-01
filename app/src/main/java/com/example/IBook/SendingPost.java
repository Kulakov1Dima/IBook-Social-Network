package com.example.IBook;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendingPost  extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects){
        MediaType mediaType = MediaType.parse("application/json");
        String url = "https://5sd6aa.deta.dev/";
        OkHttpClient httpClient = new OkHttpClient();
        String jsonStr = "{\n" +
                "  \"meta\": {\n" +
                "    \"locale\": \"ru-RU\",\n" +
                "    \"timezone\": \"UTC\",\n" +
                "    \"client_id\": \"ru.yandex.searchplugin/7.16 (none none; android 4.4.2)\",\n" +
                "    \"interfaces\": {\n" +
                "      \"screen\": {},\n" +
                "      \"payments\": {},\n" +
                "      \"account_linking\": {}\n" +
                "    }\n" +
                "  },\n" +
                "  \"session\": {\n" +
                "    \"message_id\": 1,\n" +
                "    \"session_id\": \"4cf85687-f3e0-4e65-9a8e-00f4eafde49b\",\n" +
                "    \"skill_id\": \"1c6ed86e-b5a4-4f7c-b6af-aac9a41370b6\",\n" +
                "    \"user\": {\n" +
                "      \"user_id\": \"64E502D68B1DAE7F9C01BBE39DCC7B0CA8A0292E5D9A58250DBF790D3FB40DB4\"\n" +
                "    },\n" +
                "    \"application\": {\n" +
                "      \"application_id\": \"AAAD35A25086EFD96774ECE2B214D03DA4A80DC993C901FA607EAFA7529BA935\"\n" +
                "    },\n" +
                "    \"user_id\": \"AAAD35A25086EFD96774ECE2B214D03DA4A80DC993C901FA607EAFA7529BA935\",\n" +
                "    \"new\": false\n" +
                "  },\n" +
                "  \"request\": {\n" +
                "    \"command\": \"Hello for Android\",\n" +
                "    \"original_utterance\": \"привет\",\n" +
                "    \"nlu\": {\n" +
                "      \"tokens\": [\n" +
                "        \"привет\"\n" +
                "      ],\n" +
                "      \"entities\": [],\n" +
                "      \"intents\": {}\n" +
                "    },\n" +
                "    \"markup\": {\n" +
                "      \"dangerous_context\": false\n" +
                "    },\n" +
                "    \"type\": \"SimpleUtterance\"\n" +
                "  },\n" +
                "  \"version\": \"1.0\"\n" +
                "}";
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, jsonStr))
                .build();
        Response response;
        try {
            response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.i("info","no error");
            }

        } catch (IOException e) {
            Log.i("info","error");
        }
        return null;
    }
}

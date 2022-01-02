package com.example.IBook;


import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


class SendingPost  extends AsyncTask <String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        if(strings[0].equals("authorization")) {
            MediaType mediaType = MediaType.parse("application/json");
            String url = "https://orj00w.deta.dev/";
            OkHttpClient httpClient = new OkHttpClient();
            String jsonStr = "{\n" +
                    "  \"request\": {\n" +
                    "    \"command\": \"authorization\",\n" +
                    "    \"phone\": \"" + strings[1] + "\"\n" +
                    "  },\n" +
                    "  \"version\": \"1.0\"\n" +
                    "}";
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(mediaType, jsonStr))
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                assert !response.isSuccessful() || response.body() != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

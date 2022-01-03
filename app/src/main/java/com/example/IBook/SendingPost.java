package com.example.IBook;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class SendingPost  extends AsyncTask <String, Void, Void> {
    String responseStr;

    @Override
    protected Void doInBackground(String... strings) {
        if(strings[0].equals("authorization")) {
            MediaType mediaType = MediaType.parse("application/json");
            OkHttpClient httpClient = new OkHttpClient();
            String jsonStr = "{\n" +
                    "  \"request\": {\n" +
                    "    \"command\": \"authorization\",\n" +
                    "    \"phone\": \"" + strings[1] + "\"\n" +
                    "  },\n" +
                    "  \"version\": \"1.0\"\n" +
                    "}";
            Request request = new Request.Builder()
                    .url(Configuration.url)
                    .post(RequestBody.create(mediaType, jsonStr))
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                assert response.body() != null;
                responseStr= response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            JSONObject responseObj = new JSONObject(responseStr);
            JSONObject data= responseObj.getJSONObject("response");
            if(String.valueOf(data).equals("{\"command\":\"authorization\",\"registration\":false}")) {
                // как-то нужно перейти на другое активити
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

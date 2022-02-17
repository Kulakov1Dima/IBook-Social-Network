package com.example.ibook_social_network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class SendingPost extends AsyncTask<String, Void, Void> {

    String responseStr;

    interface Callback{
        void callingBack(String s);
    }

    private final Callback callback;

    public SendingPost(Callback callback){
        this.callback = callback;
    }

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
                    "  \"version\": \"" + strings[2] + "\"\n" +
                    "}";
            Request request = new Request.Builder()
                    .url(Configuration.url)
                    .post(RequestBody.create(mediaType, jsonStr))
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                assert response.body() != null;
                responseStr= response.body().string();
                Log.e("IbookServer",responseStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void Void) {
        try {
            if(responseStr != null){
                JSONObject responseObj = new JSONObject(responseStr);
                callback.callingBack(String.valueOf(responseObj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

package com.example.ibook_social_network;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

class SendingPost extends AsyncTask<String, Void, Void> {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();
    private final Callback callback;
    private String response;

    interface Callback {
        void callingBack(String s);
    }

    public SendingPost(Callback callback) {
        this.callback = callback;
    }

    static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return (Objects.requireNonNull(client.newCall(request).execute().body())).string();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            response = post(strings[0], strings[1]);
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void Void) {
        callback.callingBack(response);
    }
}
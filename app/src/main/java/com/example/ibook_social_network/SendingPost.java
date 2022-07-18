package com.example.ibook_social_network;

import android.os.AsyncTask;
import java.io.IOException;

class SendingPost extends AsyncTask<String, Void, Void> {

    private final Callback callback;
    private String response;

    interface Callback {
        void callingBack(String s);
    }

    public SendingPost(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            response = AddPost.post(strings[0], strings[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void Void) {
        if(response!= null){
            callback.callingBack(response);
        }
    }
}
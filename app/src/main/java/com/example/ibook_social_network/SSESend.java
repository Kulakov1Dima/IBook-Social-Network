package com.example.ibook_social_network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SSESend extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL("http://checkers24.ru/ibook/messages/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("SSE", "http response: " + urlConnection.getResponseCode());

            //Object inputStream = urlConnection.getContent();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            Log.d("SSE reading stream", readStrem(inputStream) + "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("SSE activity", "Error on url openConnection: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private String readStrem(InputStream inputStream) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                Log.d("ServerSentEvents", "SSE event: " + line);
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
           if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
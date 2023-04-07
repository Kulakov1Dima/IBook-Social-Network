package com.example.ibook_social_network.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import com.example.ibook_social_network.authorization.AuthActivity;
import com.example.ibook_social_network.loading.SplashActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Network {
    public boolean checkConnections(Context context) {   //проверка соединений
        ConnectivityManager connections = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connections.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void checkServer(SplashActivity splashActivity) {    //проверка подключения к серверу
        Request request = new Request.Builder()
                .url("http://134.0.115.2/")
                .build();
        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull final Call call, @NonNull IOException e) {
                        splashActivity.startActivity(new Intent(splashActivity, ServerNotFound.class));
                        splashActivity.finish();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        if((response.body() != null ? response.body().string().length() : 0) >0){
                            CheckUpdate.checkUpdate(CheckUpdate.getVersion(splashActivity), splashActivity);
                            splashActivity.startActivity(new Intent(splashActivity, AuthActivity.class));
                            splashActivity.finish();
                        }
                    }
                });
    }
}


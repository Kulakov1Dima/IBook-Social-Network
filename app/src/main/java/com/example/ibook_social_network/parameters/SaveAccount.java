package com.example.ibook_social_network.parameters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.example.ibook_social_network.authorization.AuthActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.yandex.authsdk.YandexAuthToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SaveAccount {

    public static final String APP_PREFERENCES = "ibookSettings";
    public static String APP_ACCOUNT = "";

    public static void googleAccount(@NotNull AuthActivity authActivity) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(authActivity);
        if (acct != null) {
            APP_ACCOUNT = "google";
            SharedPreferences accountSettings = authActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = accountSettings.edit();
            editor.putString(APP_ACCOUNT, acct.getEmail());
            editor.apply();
        }
    }

    public static void yandexAccount(AuthActivity authActivity, String default_email){
        APP_ACCOUNT = "yandex";
        SharedPreferences accountSettings = authActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = accountSettings.edit();
        editor.putString(APP_ACCOUNT, default_email);
        editor.apply();
    }

    @WorkerThread
    public static void getYandexAccount(@NotNull AuthActivity authActivity, YandexAuthToken yandexAuthToken){
        Thread thread = new Thread(() -> {
            try {
                yaAuth2(authActivity, yandexAuthToken.getValue());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private static void yaAuth2(AuthActivity context, String value) {
        Request request = new Request.Builder()
                .url("https://login.yandex.ru/info?format=json&with_openid_identity=1&oauth_token=" + value)
                .build();

        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull final Call call, @NonNull IOException e) {
                        Toast.makeText(context.getApplicationContext(),
                                "Не удалось проверить авторизацию Яндекс: " + e, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        assert response.body() != null;
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            yandexAccount(context, json.getString("default_email"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}



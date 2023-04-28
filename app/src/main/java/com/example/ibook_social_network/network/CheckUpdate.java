package com.example.ibook_social_network.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ibook_social_network.loading.ShowUpdate;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckUpdate {
    public static String getVersion(Context context){
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
             version= pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void checkUpdate(String current_version, Context context){
        Request request = new Request.Builder()
                .url("http://134.0.115.2/version/")
                .build();

        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull final Call call, @NonNull IOException e) {
                        Toast.makeText(context.getApplicationContext(),
                                "Не удалось проверить обновления: " + e, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        assert response.body() != null;
                        ShowUpdate.show(response.body().string().replace("\"",""), current_version, context);
                    }
                });
    }
}

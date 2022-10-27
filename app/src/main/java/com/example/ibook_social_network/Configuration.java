package com.example.ibook_social_network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;


public class Configuration {

    public static String authUrl = "http://134.0.115.2:9000/auth";

    //applying window parameters
    public static void awp(Window window, ActionBar supportActionBar) {
        supportActionBar.hide();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  //display without borders
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    //checking the availability of the Internet
    public static boolean checkConnections(Context context) {
        ConnectivityManager connections =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connections.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //checking work server
    public static boolean checkServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 134.0.115.2");  //ip address ibook server
            return (ipProcess.waitFor() == 0);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //creating an intent for activity to select a google account
    public static Intent GoogleIntent(Context activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(activity, gso).getSignInIntent();
    }
}

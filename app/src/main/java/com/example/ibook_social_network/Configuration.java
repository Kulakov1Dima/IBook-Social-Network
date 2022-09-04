package com.example.ibook_social_network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import java.io.IOException;


public class Configuration {

    //applying window parameters
    public static void awp(Window window, ActionBar supportActionBar) {
        supportActionBar.hide();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  //display without borders
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

}

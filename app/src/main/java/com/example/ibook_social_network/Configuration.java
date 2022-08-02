package com.example.ibook_social_network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Configuration {

    //applying window parameters
    public static void awp(Window window, ActionBar supportActionBar) {
        supportActionBar.hide();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  //display without borders
    }

    //checking the availability of the Internet
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //getting google parameters
    public static Intent checkGoogle(Intent intent, Context activity) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (account != null) {
            MainActivity.email = account.getEmail();                    //костыль исправить
            intent.putExtra("nickname", account.getGivenName());
            intent.putExtra("token", MainActivity.email);
            intent.putExtra("profile_picture", account.getPhotoUrl());
            return intent;
        }else  return null;

    }

    //creating an intent for activity to select a google account
    public static Intent GoogleIntent(Context activity){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(activity, gso).getSignInIntent();
    }
}
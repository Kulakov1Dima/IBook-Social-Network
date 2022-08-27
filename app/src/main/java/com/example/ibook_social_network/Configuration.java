package com.example.ibook_social_network;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Configuration {

    public static String email = "";

    //applying window parameters
    public static void awp(Window window, ActionBar supportActionBar) {
        supportActionBar.hide();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  //display without borders
    }

    //greetings for messenger activity
    public static String formatGreeting(int time, String nickname) {
        if (time > 21) return "Доброй ночи, " + nickname;
        if (time > 16) return "Добрый вечер, " + nickname;
        if (time >= 11) return "Добрый день, " + nickname;
        if (time >= 4) return "Доброе утро, " + nickname;
        return "Доброй ночи, " + nickname;
    }

    //checking the availability of the Internet
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //add google client
    public static Intent checkGoogle(Intent intent, Context activity, SharedPreferences settings) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (account != null){
            if(settings.contains("auth"))return getParameters(intent, account);
            else return null;
        }
        else return null;
    }

    //getting google parameters
    public static Intent getParameters(Intent intent, GoogleSignInAccount account) {
        email = account.getEmail();
        intent.putExtra("nickname", account.getGivenName());
        intent.putExtra("token", account.getEmail());
        intent.putExtra("profile_picture", account.getPhotoUrl());
        return intent;
    }

    //creating an intent for activity to select a google account
    public static Intent GoogleIntent(Context activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(activity, gso).getSignInIntent();
    }

    //checking work MessageService
    public static void checkService(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
            if (serviceClass.getName().equals(service.service.getClassName())) return;
        context.startService(new Intent(context, MessageService.class));
    }

    public static Intent nextActivity(Intent oldIntent, String name, Intent intent) {
        intent.putExtra("email", oldIntent.getStringExtra("token"));
        intent.putExtra("recipient", name);
        return intent;
    }

    //authorization on ibook server
    public static void auth(Context activity, String email, String name, Uri photo) {
        new SendingPost((SendingPost.Callback) activity).execute("http://ibook.agency/ibook/auth.php", CreateJSON.JSON(email, name, String.valueOf(photo), null));
    }

    //get list of letters
    public static void listOfLetters(Context activity, Intent intent) {
        new SendingPost((SendingPost.Callback) activity).execute("http://ibook.agency/ibook/"+ intent.getStringExtra("token") + "/del.php",
                CreateJSON.JSON(intent.getStringExtra("token"), null, intent.getStringExtra("profile_picture"), null));
    }

    //sending message
    public static void sendMessage(Context activity, String token, String recipient, String photo, String message) {
        new SendingPost((SendingPost.Callback) activity).execute("http://ibook.agency/ibook/send.php",
                CreateJSON.JSON(token, recipient, photo, message));
    }
}
package com.example.ibook_social_network;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SendingPost.Callback {

    public static String email;
    int RC_SIGN_IN = 1345;
    Intent intent;

    //starting the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.awp(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        setContentView(R.layout.activity_main);
    }

    public void signIn(View view) {
        startActivityForResult(Configuration.GoogleIntent(this), RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            email = account.getEmail();
            new SendingPost(this).execute("http://checkers24.ru/ibook/auth.php", email);
            intent = new Intent(MainActivity.this, Messenger.class);
            intent.putExtra("nickname", account.getGivenName());
            intent.putExtra("token", email);
            intent.putExtra("profile_picture", account.getPhotoUrl());
        } catch (ApiException e) {
            Log.w("Google Account", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void callingBack(String s) {
        if (s.equals(email)) {
            startActivity(intent);
            finish();
        }
    }
}
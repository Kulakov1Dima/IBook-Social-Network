package com.example.ibook_social_network;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SendingPost.Callback {

    public static String email;
    Intent intent;

    //starting the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.awp(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        setContentView(R.layout.activity_main);
        findViewById(R.id.sign_in_button)
                .setOnClickListener(v -> someActivityResultLauncher.launch((Configuration.GoogleIntent(this))));
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleSignInResult(task);
                }
            });

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
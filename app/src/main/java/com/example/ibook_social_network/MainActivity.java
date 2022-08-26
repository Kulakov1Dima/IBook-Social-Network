package com.example.ibook_social_network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SendingPost.Callback {

    public static final String APP_PREFERENCES = "settings";
    SharedPreferences settings;
    GoogleSignInAccount account;

    //starting the screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.awp(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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
            account = completedTask.getResult(ApiException.class);
            Configuration.auth(this, account.getEmail(), account.getDisplayName(), account.getPhotoUrl());
        } catch (ApiException e) {
            Toast.makeText(this, "Не удалось войти в аккаунт Google", Toast.LENGTH_SHORT).show();
        }
    }

    //sign in app
    @Override
    public void callingBack(String s) {
        System.out.println(s);
        if (s.equals("error json")) Toast.makeText(this, "Повторите позже", Toast.LENGTH_SHORT).show();
        if (s.equals(account.getEmail())) {
            saveAuth();
            startActivity(Configuration.getParameters(new Intent(MainActivity.this, Messenger.class), account));
            startService(new Intent(this, MessageService.class));
            finish();
            Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAuth(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("auth", "true");
        editor.apply();
    }
}
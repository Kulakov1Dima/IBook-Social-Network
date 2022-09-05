package com.example.ibook_social_network;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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
                    handleSignInResult();
                }
            });

    private void handleSignInResult() {
        startActivity(new Intent(this, SplashScreen.class));
        finish();
    }
}
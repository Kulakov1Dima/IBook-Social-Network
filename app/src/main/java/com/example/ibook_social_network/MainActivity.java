package com.example.ibook_social_network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Ibook - приложение соцсети.
 * 21.10.2021  01:09 Кулаков Дмитрий
 * 0.1 (102)
 */

public class MainActivity extends AppCompatActivity implements SendingPost.Callback {

    public static final String APP_PREFERENCES = "accountSettings";
    public static final String ACCOUNT_PREFERENCES_NAME = "Nickname";
    public static final String ACCOUNT_PREFERENCES_PASSWORD = "password";

    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        checkDataLastAuthorization();
        autocorrectPhone();
    }

    void autocorrectPhone() {
        EditText phone = findViewById(R.id.editTextPhone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    String getPhone() {
        EditText phone = findViewById(R.id.editTextPhone);
        return phone.getText()
                .toString()
                .replaceAll(" ", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");
    }

    String getPassword() {
        TextView password = findViewById(R.id.editTextTextPassword);
        return password.getText().toString();
    }

    boolean checkAuthorization() {
        Config ErrorToastConfiguration = new Config();
        if (!(getPhone().length() == ErrorToastConfiguration.lengthLogin)) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.errorPhone,
                    Toast.LENGTH_SHORT).show();
        }
        if (getPassword().length() < ErrorToastConfiguration.lengthPassword) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.errorPassword,
                    Toast.LENGTH_SHORT).show();
        }
        return (getPhone().length() == ErrorToastConfiguration.lengthLogin) &&
                getPassword().length() >= ErrorToastConfiguration.lengthPassword;
    }

    public void authorization(View view) {
        Config ErrorToastConfiguration = new Config();
        if (checkAuthorization()) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.waitMessage,
                    Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(ACCOUNT_PREFERENCES_NAME, getPhone());
            editor.putString(ACCOUNT_PREFERENCES_PASSWORD, getPassword());
            editor.apply();
            new SendingPost(this).execute("authorization", getPhone(), getPassword(), "1.0v");
        }
    }

    @Override
    public void callingBack(String dataResponse) {
        Config ErrorToastConfiguration = new Config();
        if (Boolean.parseBoolean(dataResponse)) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.authorization,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
            intent.putExtra("myPhone", mSettings.getString(ACCOUNT_PREFERENCES_NAME, ""));
            startActivity(intent);
        } else {
            if (checkAuthorization()) {
                Toast.makeText(getApplicationContext(),
                        ErrorToastConfiguration.noAuthorization,
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        ErrorToastConfiguration.oldSession,
                        Toast.LENGTH_SHORT).show();
            }
            mSettings.edit().clear().apply();
        }
    }

    void checkDataLastAuthorization() {
        if (mSettings.contains(ACCOUNT_PREFERENCES_NAME) && mSettings.contains(ACCOUNT_PREFERENCES_PASSWORD)) {
            new SendingPost(this).execute("authorization", mSettings.getString(ACCOUNT_PREFERENCES_NAME, ""), mSettings.getString(ACCOUNT_PREFERENCES_PASSWORD, ""), "1.0v");
        }
    }

}
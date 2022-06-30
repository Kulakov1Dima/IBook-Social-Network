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
 * 0.1 (104)
 */

public class MainActivity extends AppCompatActivity implements SendingPost.Callback {

    /*Постоянные настройки пользователя*/
    public static final String APP_PREFERENCES = "accountSettings";
    public static final String ACCOUNT_PREFERENCES_NAME = "Nickname";
    public static final String ACCOUNT_PREFERENCES_PASSWORD = "password";

    public static SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        checkDataLastAuthorization();
        autocorrectPhone();
    }
    /*установка формата ввода телефона*/
    void autocorrectPhone() {
        EditText phone = findViewById(R.id.editTextPhone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    /*получение телефона*/
    String getPhone() {
        EditText phone = findViewById(R.id.editTextPhone);
        return phone.getText()
                .toString()
                .replaceAll(" ", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");   //очистка от ненужных символов, созданных в функции autocorrectPhone()
    }

    /*получение пароля*/
    String getPassword() {
        TextView password = findViewById(R.id.editTextTextPassword);
        return password.getText().toString();
    }

    /*проверка длины введённых данных*/
    boolean checkAuthorization() {
        Config ErrorToastConfiguration = new Config();
        if (!(getPhone().length() == ErrorToastConfiguration.lengthLogin)) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.errorPhone,
                    Toast.LENGTH_SHORT).show(); //неверное количество символов телефона
        }
        if (getPassword().length() < ErrorToastConfiguration.lengthPassword) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.errorPassword,
                    Toast.LENGTH_SHORT).show(); //неверное количество символов пароля
        }
        return (getPhone().length() == ErrorToastConfiguration.lengthLogin) &&
                getPassword().length() >= ErrorToastConfiguration.lengthPassword;   // если длина вводимых строк совпадает возращает true
    }

    /*функция, вызываемая при нажатии кнопки*/
    public void authorization(View view) {
        Config ErrorToastConfiguration = new Config();
        if (checkAuthorization()) {
            Toast.makeText(getApplicationContext(),
                    ErrorToastConfiguration.waitMessage,
                    Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = mSettings.edit();        //запись логина и пароля для автоматической авторизации (checkDataLastAuthorization())
            editor.putString(ACCOUNT_PREFERENCES_NAME, getPhone());
            editor.putString(ACCOUNT_PREFERENCES_PASSWORD, getPassword());
            editor.apply();
            new SendingPost(this).execute("authorization", getPhone(), getPassword(), "1.0v");
        }
    }

    //ответ от сервера
    @Override
    public void callingBack(String dataResponse) {
        if (!dataResponse.equals("404")) {
            Config ErrorToastConfiguration = new Config();
            if (dataResponse.equals("{\"accessToken\":true}")) {
                Toast.makeText(getApplicationContext(),
                        ErrorToastConfiguration.authorization,
                        Toast.LENGTH_SHORT).show();
                startMessenger();
                startIbookService();
                finish();
            } else {
                if (checkAuthorization()) { //неверный логин или пароль
                    Toast.makeText(getApplicationContext(),
                            ErrorToastConfiguration.noAuthorization,
                            Toast.LENGTH_SHORT).show();
                } else { //checkDataLastAuthorization() получила устаревший логин и пароль
                    Toast.makeText(getApplicationContext(),
                            ErrorToastConfiguration.oldSession,
                            Toast.LENGTH_SHORT).show();
                }
                mSettings.edit().clear().apply();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Сервер недоступен",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*автоматическая авторизация*/
    void checkDataLastAuthorization() {
        if (mSettings.contains(ACCOUNT_PREFERENCES_NAME) && mSettings.contains(ACCOUNT_PREFERENCES_PASSWORD)) {
            new SendingPost(this).execute("authorization", mSettings.getString(ACCOUNT_PREFERENCES_NAME, ""), mSettings.getString(ACCOUNT_PREFERENCES_PASSWORD, ""), "1.0v");
        }
    }

    void startMessenger() {
        Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
        intent.putExtra("myPhone", mSettings.getString(ACCOUNT_PREFERENCES_NAME, ""));
        startActivity(intent);
    }

    void startIbookService() {
        stopService(new Intent(this, IbookMessengerService.class));   //остановка фоновой службы опроса сервера (на случай если она уже была запущена)
        startService(new Intent(this, IbookMessengerService.class));  //запуск
    }
}

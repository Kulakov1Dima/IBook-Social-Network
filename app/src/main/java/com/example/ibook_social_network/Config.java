package com.example.ibook_social_network;

import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

class Config {
    byte lengthLogin = 10;
    byte lengthPassword = 6;
    String errorPhone = "Неверный телефон";
    String errorPassword = "Миннимальная длина пароля " + lengthPassword + " символов";
    String waitMessage = "Подождите...";
    String defaultUrl = "http://checkers24.ru/ibook/";
    String url = MainActivity.get_settings_server();
    String authUrl;
    public String jsonStr;
    public String jsonAuthorization;
    String authorization = "Добро пожаловать!";
    String noAuthorization = "Проверьте правильность ввода телефона или пароля";
    String oldSession = "Ваш пароль или логин устарел";
    String errorServer = "Ошибка сервера";
    //все запоросы приложения на сервер вот в таком виде:
    public Config(String[] dataToServer) {
        jsonStr = "{\n" +
                "  \"request\": {\n" +
                "    \"command\": \"" + dataToServer[0] + "\",\n" +
                "    \"phone\": \"+7" + dataToServer[1] + "\"\n" +
                "  },\n" +
                "  \"version\": \"" + dataToServer[2] + "\"\n" +
                "}";
        jsonAuthorization = "[\"msgReader\",\" msgCreator\", \"msgUpdater\", \"msgDeleter\", \"chatReader\", \"chatCreator\", \"chatUpdater\", \"chatDeleter\", \"userReader\", \"userCreator\", \"userUpdater\", \"userDeleter\", \"postReader\", \"postCreator\", \"postUpdater\", \"postDeleter\"]";
        authUrl =url;
        //authUrl = url+"auth/token/data_as_body?username=%2B7"+dataToServer[1]+"&password="+dataToServer[2];
    }
    Config() {
    }
    /*Применение цвета к панели навигации*/
    public void setStatusBarColor(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
    }
}
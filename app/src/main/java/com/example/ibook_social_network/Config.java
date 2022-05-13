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
    String defaultUrl = "http://f0646895.xsph.ru/";
    String url = MainActivity.get_settings_server();
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
        jsonAuthorization =
                "<form method=\"POST\" action=\"http://192.168.0.101:6005/auth/token\">\n" +
                        "<h1>accept= \"application/json\"</h1>\n"+
                        "<h1>Content-Type= \"multipart/form-data\"</h1>\n"+
                        "<p>username=\"+79270969849\"</p>\n"+
                        "<p>password=\"ibook1422\"</p>\n"+
                        "<p>scopes=\"\"</p>\n"+
                        "</form>";
    }
    Config() {
    }
    /*Применение цвета к панели навигации*/
    public void setStatusBarColor(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
    }
}
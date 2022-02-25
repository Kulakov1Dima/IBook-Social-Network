package com.example.ibook_social_network;

class Config {
    byte lengthLogin = 10;
    byte lengthPassword = 6;
    String errorPhone = "Неверный телефон";
    String errorPassword = "Миннимальная длина пароля " + lengthPassword + " символов";
    String waitMessage = "Подождите...";
    String url = "https://ibooksn.000webhostapp.com/";
    public String jsonStr;
    String authorization = "Добро пожаловать!";
    String noAuthorization = "Проверьте правильность ввода телефона или пароля";
    String oldSession = "Ваш пароль или логин устарел";


    public Config(String[] dataToServer) {
        jsonStr = "{\n" +
                "  \"request\": {\n" +
                "    \"command\": \"" + dataToServer[0] + "\",\n" +
                "    \"phone\": \"+7" + dataToServer[1] + "\"\n" +
                "  },\n" +
                "  \"version\": \"" + dataToServer[2] + "\"\n" +
                "}";
    }

    Config() {
    }
}
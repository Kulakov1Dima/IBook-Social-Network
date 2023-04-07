package com.example.ibook_social_network.authorization;

import android.content.Intent;
import com.yandex.authsdk.YandexAuthLoginOptions;
import com.yandex.authsdk.YandexAuthOptions;
import com.yandex.authsdk.YandexAuthSdk;

public class YandexAuth extends AuthActivity {
    static YandexAuthSdk sdk;
    public static Intent auth(AuthActivity authActivity) {
        sdk = new YandexAuthSdk(new YandexAuthOptions(authActivity, true));
        final YandexAuthLoginOptions.Builder loginOptionsBuilder = new YandexAuthLoginOptions.Builder();
        return sdk.createLoginIntent(loginOptionsBuilder.build());
    }
}

package com.test.charity_api.util.zalopay;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public static final Map<String, String> MERCHANT_INFO = new HashMap<String, String>() {
        {
            put("app_id", "2554");
            put("key1", "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn");
            put("key2", "trMrHtvjo6myautxDUiAcYsVtaeQ8nhf");
        }
    };
    public static final String ENDPOINT = "https://sb-openapi.zalopay.vn/v2/create";
    public static final String REDIRECT_BASE_URL = "http://localhost:5173/campaign";
    public static final String CALLBACK_URL = "https://1dbd-103-199-35-146.ngrok-free.app/payment/zalopay/callback";
}

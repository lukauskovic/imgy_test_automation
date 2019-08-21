package com.rest.autotests.utils;

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(Config.class.getResourceAsStream("/config-main.properties"));
            String configName = System.getProperty("configName");
            if (configName != null) {
                PROPERTIES.load(Config.class.getResourceAsStream("/config-" + configName + ".properties"));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static final String USER_EMAIL = get("user.email");
    public static final String USER_PASSWORD = get("user.password");
    public static final String USER_USERNAME = get("user.username");
    public static final String BASE_URL = get("base.url");
    public static final String STATIC_TOKEN = get("static.token");

    public static String get(String key) {
        if (!PROPERTIES.containsKey(key)) {
            throw new IllegalStateException("Required configuration property " + key + " is not found.");
        }

        return PROPERTIES.getProperty(key);
    }
}

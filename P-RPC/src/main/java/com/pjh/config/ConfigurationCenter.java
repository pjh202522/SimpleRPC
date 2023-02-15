package com.pjh.config;

import java.util.ResourceBundle;

/**
 * @author yueyinghaibao
 */
public class ConfigurationCenter {
    private static ResourceBundle resource;

    static {
        resource = ResourceBundle.getBundle("config");
    }

    public static Object get(String key) {
        return resource.getObject(key);
    }
}

package com.krce.utils;

import java.io.InputStream;
import java.util.Properties;
public class ConfigReader {
    private static Properties properties;
    static {
        try {
            properties = new Properties();
            InputStream input =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");
            if (input == null) {
                throw new RuntimeException("config.properties file not found");
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
package com.elisa.polystar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {
    private static final Logger logger = Logger.getLogger(ConfigLoader.class.getName());
    private static final Properties config = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties file.");
            }
            config.load(input);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to load configuration", ex);
            throw new RuntimeException("Failed to load configuration", ex);
        }
    }

    public static String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(config.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid number format for key: " + key, e);
            return defaultValue;
        }
    }
}

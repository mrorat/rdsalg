package com.quasar.rdsalg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesLoader {
    private final static Logger logger = Logger.getLogger(PropertiesLoader.class.getCanonicalName());

    public static final String PROPERTIES_FILE = "malg.properties";
    private static Properties properties = null;

    public static synchronized Properties loadProperties(ClassLoader classLoader) {
        if (properties == null) {
            InputStream in = classLoader.getResourceAsStream(PROPERTIES_FILE);
            if (in != null) {
                properties = new Properties();
                try {
                    properties.load(in);
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info(String.format("Unable to find %s", PROPERTIES_FILE));
            }
        }
        return properties;
    }

    public static String getProperty(String envOrPropertyName) {
        String systemEnv = System.getProperty(envOrPropertyName);
        if (systemEnv != null)
            return systemEnv;
        if (properties != null)
            return properties.getProperty(envOrPropertyName);
        else
            throw new RuntimeException("properties file missing, trying to get property ["
                    + envOrPropertyName + "]");
    }
}

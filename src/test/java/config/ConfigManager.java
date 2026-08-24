package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigManager.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load configuration",
                    e
            );
        }
    }

    private ConfigManager() {
    }

    public static String get(String key) {

        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Configuration property not found: " + key
            );
        }

        return value;
    }
}

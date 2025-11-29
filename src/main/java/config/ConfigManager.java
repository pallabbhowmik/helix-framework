package com.helix.automation.framework.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties props = new Properties();

    static {
        load();
    }

    private ConfigManager() {
        // utility class
    }

    private static void load() {
        // 1) Read env.config from project root
        Path envFile = Paths.get(System.getProperty("user.dir"), ".env.config");
        if (Files.exists(envFile)) {
            try (BufferedReader br = Files.newBufferedReader(envFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                        continue;
                    }
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    props.setProperty(key, value);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read env.config", e);
            }
        }

        // 2) Overlay environment variables (optional, but handy for CI)
        System.getenv().forEach((k, v) -> {
            if (v != null && !v.isEmpty()) {
                props.setProperty(k, v);
            }
        });

        // 3) Overlay system properties (-D...) if provided
        System.getProperties().forEach((k, v) -> {
            if (k instanceof String && v != null) {
                props.setProperty((String) k, v.toString());
            }
        });
    }

    // --- Generic getter ---
    public static String get(String key) {
        return props.getProperty(key);
    }

    // --- Convenience getters for your keys ---

    public static String getEnv() {
        return props.getProperty("ENV", "dev");
    }

    public static String getBaseUrl() {
        return props.getProperty("BASE_URL");
    }

    public static String getApiBaseUrl() {
        return props.getProperty("API_BASE_URL");
    }

    public static String getBrowser() {
        return props.getProperty("BROWSER", "chrome");
    }

    public static String getRunType() {
        return props.getProperty("RUN_TYPE", "FULL");
    }

    public static String getUsername() {
        return props.getProperty("USERNAME");
    }

    public static String getPassword() {
        return props.getProperty("PASSWORD");
    }
    public static long getTimeout() {
        return Long.parseLong(props.getProperty("TIMEOUT"));
    }
}

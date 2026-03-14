package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized configuration manager with layered resolution:
 * <ol>
 *   <li>.env.config file (project root)</li>
 *   <li>OS environment variables</li>
 *   <li>JVM system properties (-D flags)</li>
 * </ol>
 * Each layer overrides the previous, making it CI/CD-friendly.
 */
public final class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties props = new Properties();

    static {
        load();
    }

    private ConfigManager() {
        // utility class
    }

    private static void load() {
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
                log.info("Loaded configuration from: {}", envFile.toAbsolutePath());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read .env.config", e);
            }
        } else {
            log.warn(".env.config not found at: {}. Using defaults and environment variables.",
                    envFile.toAbsolutePath());
        }

        // Overlay environment variables (useful for CI/CD)
        System.getenv().forEach((k, v) -> {
            if (v != null && !v.isEmpty()) {
                props.setProperty(k, v);
            }
        });

        // Overlay system properties (-D flags take highest priority)
        System.getProperties().forEach((k, v) -> {
            if (k instanceof String && v != null) {
                props.setProperty((String) k, v.toString());
            }
        });
    }

    // ── Generic getter ──

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    // ── Typed convenience getters ──

    public static Environment getEnv() {
        return Environment.fromString(props.getProperty("HELIX_ENV", "dev"));
    }

    public static String getBaseUrl() {
        return props.getProperty("HELIX_BASE_URL");
    }

    public static String getApiBaseUrl() {
        return props.getProperty("HELIX_API_BASE_URL");
    }

    public static BrowserType getBrowserType() {
        return BrowserType.fromString(props.getProperty("HELIX_BROWSER", "chrome"));
    }

    public static String getBrowser() {
        return getBrowserType().getName();
    }

    public static String getRunType() {
        return props.getProperty("HELIX_RUN_TYPE", "FULL");
    }

    public static String getUsername() {
        return props.getProperty("HELIX_USERNAME");
    }

    public static String getPassword() {
        return props.getProperty("HELIX_PASSWORD");
    }

    public static long getTimeout() {
        String value = props.getProperty("HELIX_TIMEOUT", "10");
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.warn("Invalid HELIX_TIMEOUT='{}', defaulting to 10", value);
            return 10;
        }
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(props.getProperty("headless", "false"));
    }

    /**
     * Validates that all required properties are set.
     * Call this at test suite start to fail fast.
     */
    public static void validate(String... requiredKeys) {
        for (String key : requiredKeys) {
            String value = props.getProperty(key);
            if (value == null || value.isBlank()) {
                throw new IllegalStateException(
                        "Required config property '" + key + "' is not set. Check .env.config file.");
            }
        }
    }
}

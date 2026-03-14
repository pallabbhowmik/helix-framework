package core;

import config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Writes environment metadata into allure-results/environment.properties.
 * This data is displayed in the Allure report's "Environment" widget,
 * showing execution context (browser, OS, Java version, etc.).
 */
public final class AllureEnvironmentWriter {

    private static final Logger log = LoggerFactory.getLogger(AllureEnvironmentWriter.class);

    private AllureEnvironmentWriter() {
        // utility class
    }

    public static void write() {
        String allureDir = System.getProperty("allure.results.directory", "build/allure-results");
        Path envFile = Paths.get(allureDir, "environment.properties");

        try {
            Files.createDirectories(envFile.getParent());

            Properties envProps = new Properties();
            envProps.setProperty("Browser", ConfigManager.getBrowser());
            envProps.setProperty("Headless", String.valueOf(ConfigManager.isHeadless()));
            envProps.setProperty("Environment", ConfigManager.getEnv().getName());
            envProps.setProperty("Base.URL", String.valueOf(ConfigManager.getBaseUrl()));
            envProps.setProperty("API.Base.URL", String.valueOf(ConfigManager.getApiBaseUrl()));
            envProps.setProperty("OS", System.getProperty("os.name") + " " + System.getProperty("os.arch"));
            envProps.setProperty("Java.Version", System.getProperty("java.version"));

            try (OutputStream os = Files.newOutputStream(envFile)) {
                envProps.store(os, "Allure Environment Properties");
            }

            log.info("Allure environment.properties written to: {}", envFile.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to write Allure environment.properties", e);
        }
    }
}

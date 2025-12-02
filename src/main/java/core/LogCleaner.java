package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

public class LogCleaner {

    private static final Logger log = LoggerFactory.getLogger(LogCleaner.class);

    private static final Path LOG_FILE = Paths.get("logs", "automation.log");

    public static void clean() {
        try {
            if (Files.exists(LOG_FILE)) {
                Files.delete(LOG_FILE);
                log.info("Previous log file deleted: {}", LOG_FILE.toAbsolutePath());
            } else {
                log.info("No previous log file found — nothing to delete.");
            }
        } catch (IOException e) {
            log.error("Unable to delete log file: {}", LOG_FILE.toAbsolutePath(), e);
        }
    }
}

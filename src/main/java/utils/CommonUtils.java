package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {
        // utility class
    }

    public static String takeScreenshot(WebDriver driver, String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = getUniqueFileName("screenshot", "png");
        Path destination = path.resolve(fileName);

        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Files.write(destination, screenshotBytes);

        Allure.addAttachment(fileName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
        log.info("Screenshot saved: {}", destination.toAbsolutePath());

        return destination.toString();
    }

    public static String getUniqueFileName(String prefix, String extension) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        return prefix + "_" + timestamp + "." + extension;
    }
}

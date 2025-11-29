package utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    public static String takeScreenshot(WebDriver driver, String folderPath) throws IOException {

        // Create directory if not exists
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Generate filename
        String fileName = getUniqueFileName("screenshot", "png");
        Path destination = path.resolve(fileName);

        // Capture and store
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, destination.toFile());

        // Attach to Allure report
        Allure.addAttachment(fileName, Files.newInputStream(destination));

        return destination.toString();
    }

    public static String getUniqueFileName(String prefix, String extension) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        return prefix + "_" + timestamp + "." + extension;
    }
}

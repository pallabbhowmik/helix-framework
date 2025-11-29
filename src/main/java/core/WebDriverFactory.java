package com.helix.automation.framework.core;

import com.helix.automation.framework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverFactory {

    public static WebDriver create() {

        String browser = ConfigManager.getBrowser();
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        WebDriver driver;

        switch (browser.toLowerCase()) {

            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                driver = new org.openqa.selenium.firefox.FirefoxDriver();
                break;
            }

            case "edge": {
                WebDriverManager.edgedriver().setup();
                driver = new org.openqa.selenium.edge.EdgeDriver();
                break;
            }

            case "chrome":
            default: {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();

                    // Enable headless if set via system argument
                    if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                        options.addArguments("--headless=new");
                    }

                    // 👉 Enable incognito mode
                    options.addArguments("--incognito");

                    // Optional stability boosts
                    options.addArguments("--disable-infobars");
                    options.addArguments("--disable-notifications");
                    options.addArguments("--disable-extensions");

                    driver = new ChromeDriver(options);
                }
        }

        long timeout = ConfigManager.getTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        return driver;
    }
}

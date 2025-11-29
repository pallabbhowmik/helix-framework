package core;

import config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverFactory {

    public static WebDriver create() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
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
                    if (headless) {
                        options.addArguments("--headless=new");
                    }

                    driver = new ChromeDriver(options);
                    break;
                    }
        }

        long timeout = ConfigManager.getTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        return driver;
    }
}

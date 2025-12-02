package core;

import config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WebDriverFactory {

    private static final Logger log = LoggerFactory.getLogger(WebDriverFactory.class);

    public static WebDriver create() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        String browser = ConfigManager.getBrowser();

        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        log.info("Creating WebDriver instance: browser='{}', headless={}", browser, headless);

        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                log.debug("Initializing FirefoxDriver with options: {}", options);
                driver = new FirefoxDriver(options);
                break;
            }

            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                log.debug("Initializing EdgeDriver with options: {}", options);
                driver = new EdgeDriver(options);
                break;
            }

            case "chrome":
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                log.debug("Initializing ChromeDriver with options: {}", options);
                driver = new ChromeDriver(options);
                break;
            }
        }


       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        log.info("WebDriver created and configured successfully");
        return driver;
    }
}

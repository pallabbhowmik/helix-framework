package core;

import config.BrowserType;
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

/**
 * Factory for creating WebDriver instances.
 * Uses the Factory design pattern to encapsulate browser-specific initialization.
 * Thread-safe when combined with {@link DriverManager} ThreadLocal storage.
 */
public final class WebDriverFactory {

    private static final Logger log = LoggerFactory.getLogger(WebDriverFactory.class);

    private WebDriverFactory() {
        // prevent instantiation
    }

    public static WebDriver create() {
        boolean headless = ConfigManager.isHeadless();
        BrowserType browserType = ConfigManager.getBrowserType();

        log.info("Creating WebDriver: browser='{}', headless={}", browserType.getName(), headless);

        WebDriver driver;

        switch (browserType) {
            case FIREFOX:
                driver = createFirefox(headless);
                break;
            case EDGE:
                driver = createEdge(headless);
                break;
            case CHROME:
            default:
                driver = createChrome(headless);
                break;
        }

        driver.manage().window().maximize();
        log.info("WebDriver created and configured for: {}", browserType.getName());
        return driver;
    }

    private static WebDriver createChrome(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*"
        );
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        log.debug("ChromeDriver options configured");
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        log.debug("FirefoxDriver options configured");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdge(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications", "--no-sandbox");
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        log.debug("EdgeDriver options configured");
        return new EdgeDriver(options);
    }
}

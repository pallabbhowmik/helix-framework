package pages;

import core.BasePage;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    private final By loginButton = By.xpath("//div/a[text() = 'Login']");

    public HomePage() {
        super();
    }

    public void clickLogin() {
        log.info("Clicking Login button on Home page");
        click(loginButton);
    }
}

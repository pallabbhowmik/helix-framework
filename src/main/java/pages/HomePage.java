package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    private final By loginButton = By.xpath("//div/a[text() = 'Login']");

    @Step("Click Login button on Home page")
    public LoginPage clickLogin() {
        log.info("Clicking Login button on Home page");
        click(loginButton);
        return new LoginPage();
    }
}

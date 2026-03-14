package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    private final By userName  = By.id("email");
    private final By passWord  = By.id("password");
    private final By signInBtn = By.xpath("//button[text()='Sign In']");

    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email) {
        log.info("Entering email on Login page: {}", email);
        type(userName, email);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        log.info("Entering password on Login page");
        type(passWord, password);
        return this;
    }

    @Step("Click Sign In button")
    public AppPage clickSignInButton() {
        log.info("Clicking Sign In button on Login page");
        click(signInBtn);
        return new AppPage();
    }

    @Step("Login with email: {email}")
    public AppPage loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickSignInButton();
    }
}

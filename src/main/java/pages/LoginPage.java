package pages;

import core.BasePage;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    private final By userName  = By.id("email");
    private final By passWord  = By.id("password");
    private final By signInBtn = By.xpath("//button[text()='Sign In']");

    public LoginPage() {
        super();
    }

    public void enterEmail(String email) {
        log.info("Entering email on Login page: {}", email);
        type(userName, email);
    }

    public void enterPassword(String password) {
        log.info("Entering password on Login page");
        type(passWord, password);
    }

    public void clickSignInButton() {
        log.info("Clicking Sign In button on Login page");
        click(signInBtn);
    }
}

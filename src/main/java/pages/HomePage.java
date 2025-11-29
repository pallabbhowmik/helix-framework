package pages;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = "//div/a[text() = 'Login']")
    private WebElement loginButton;

    public HomePage() {
        super(); // initializes driver + wait from BasePage
        PageFactory.initElements(driver, this);
    }

    public void clickLogin() {
        log.info("Clicking Login button on Home page");
        loginButton.click();
    }
}

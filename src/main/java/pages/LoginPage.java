package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement passWord;

    private final By signInBtn = By.xpath("//button[text()='Sign In']");

    public LoginPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        type(By.id("email"), email);
    }

    public void enterPassword(String password) {
        type(By.id("password"), password);
    }

    public void clickSignInButton() {
        click(signInBtn);
    }
}

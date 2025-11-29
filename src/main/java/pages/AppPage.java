
package pages;

import config.ConfigManager;
import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AppPage extends BasePage {
    @FindBy (xpath = "//button[text() = 'Launch dashboard']")
    public WebElement btnLaunchDashboard;
    @FindBy (xpath = "//button[text() = 'Browse challenges']")
    public WebElement btnBrowseChallenges;
    @FindBy (xpath = "//h3[text() = 'Product discovery']")
    public WebElement btnProductDiscovery;

    public AppPage(){
        super();
        PageFactory.initElements(driver, this);
    }


}

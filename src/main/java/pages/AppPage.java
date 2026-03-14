package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(AppPage.class);

    private final By btnLaunchDashboard  = By.xpath("//button[text() = 'Launch dashboard']");
    private final By btnBrowseChallenges = By.xpath("//button[text() = 'Browse challenges']");
    private final By btnProductDiscovery = By.xpath("//h3[text() = 'Product discovery']");

    @Step("Verify Launch Dashboard button is visible")
    public boolean isLaunchDashboardVisible() {
        boolean visible = isVisible(btnLaunchDashboard);
        log.info("Launch Dashboard button visible: {}", visible);
        return visible;
    }

    @Step("Verify Browse Challenges button is enabled")
    public boolean isBrowseChallengesEnabled() {
        boolean enabled = waitForVisible(btnBrowseChallenges).isEnabled();
        log.info("Browse Challenges button enabled: {}", enabled);
        return enabled;
    }

    @Step("Verify Product Discovery section is visible")
    public boolean isProductDiscoveryVisible() {
        boolean visible = isVisible(btnProductDiscovery);
        log.info("Product Discovery section visible: {}", visible);
        return visible;
    }
}

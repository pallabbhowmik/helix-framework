package pages;

import core.BasePage;
import org.openqa.selenium.By;

public class AppPage extends BasePage {

    private final By btnLaunchDashboard = By.xpath("//button[text() = 'Launch dashboard']");
    private final By btnBrowseChallenges = By.xpath("//button[text() = 'Browse challenges']");
    private final By btnProductDiscovery = By.xpath("//h3[text() = 'Product discovery']");

    public AppPage() {
        super();
    }

    public boolean isLaunchDashboardVisible() {
        return isVisible(btnLaunchDashboard);
    }

    public boolean isBrowseChallengesEnabled() {
        return waitForVisible(btnBrowseChallenges).isEnabled();
    }

    public boolean isProductDiscoveryVisible() {
        return isVisible(btnProductDiscovery);
    }
}

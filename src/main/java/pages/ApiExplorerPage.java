package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class ApiExplorerPage extends BasePage {
    // Locators
    private final By header = By
            .xpath("//h1[contains(.,'API Explorer') or contains(.,'API')] | //*[contains(@class,'api-header')]");
    private final By baseUrl = By.xpath("//*[contains(text(),'Base URL')] | //*[contains(@class,'api-base-url')]");
    private final By endpointList = By
            .xpath("//div[contains(@class,'endpoint-list')] | //*[contains(@class,'endpoints')]");
    private final By codeSamplePanel = By
            .xpath("//div[contains(@class,'code-sample')] | //*[contains(@class,'code-panel')]");
    private final By sendRequestButton = By
            .xpath("//button[contains(.,'Send')] | //button[contains(@class,'send-request')]");
    private final By responseStatus = By
            .xpath("//*[contains(@class,'response-status')] | //*[contains(text(),'Status')]");
    private final By responseBody = By.xpath("//*[contains(@class,'response-body')] | //pre | //code");
    private final By quickAuthTestUser = By
            .xpath("//button[contains(.,'Test User')] | //*[contains(@data-test,'quick-auth')]");

    public ApiExplorerPage open() {
        driver.get(ConfigManager.getBaseUrl() + "/app/api-explorer");
        return this;
    }

    public boolean isHeaderVisible() {
        try {
            return waitForVisible(header).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBaseUrlVisible() {
        try {
            return isVisible(baseUrl);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEndpointGroupVisible() {
        try {
            return isVisible(endpointList);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCodeSamplePanelVisible() {
        try {
            return isVisible(codeSamplePanel);
        } catch (Exception e) {
            return false;
        }
    }

    public void selectEndpoint(String endpoint) {
        By endpointLocator = By.xpath("//div[contains(@class,'endpoint') and contains(text(), '" + endpoint
                + "')] | //*[contains(.,'" + endpoint + "')]");
        click(endpointLocator);
    }

    public void clickSendRequest() {
        click(sendRequestButton);
    }

    public String getResponseStatus() {
        try {
            return getText(responseStatus);
        } catch (Exception e) {
            return "";
        }
    }

    public String getResponseBody() {
        try {
            return getText(responseBody);
        } catch (Exception e) {
            return "";
        }
    }

    public void setQuickAuthToTestUser() {
        click(quickAuthTestUser);
    }
}

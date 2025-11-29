package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class NotesPage extends BasePage {
    // Locators
    private final By heading = By.xpath("//h1[contains(.,'Notes') or contains(.,'My Notes')]");
    private final By composeNoteButton = By.xpath("//button[contains(.,'Compose') or contains(.,'New Note')]");
    private final By openLibraryButton = By.xpath("//button[contains(.,'Open my library') or contains(.,'Library')]");
    private final By sortDropdown = By.xpath("//select[contains(@class,'sort')] | //*[contains(@aria-label,'sort')]");
    private final By viewToggle = By
            .xpath("//button[contains(@class,'view-toggle')] | //*[contains(@aria-label,'view')]");
    private final By noteTypeFilter = By
            .xpath("//div[contains(@class,'note-type-filter')] | //*[contains(@aria-label,'filter')]");
    private final By focusTags = By.xpath("//div[contains(@class,'tags-area')] | //*[contains(@class,'tags')]");
    private final By trendingTopics = By
            .xpath("//div[contains(@class,'trending-topics')] | //*[contains(@class,'trending')]");

    public NotesPage open() {
        driver.get(ConfigManager.getBaseUrl() + "/app");
        return this;
    }

    public boolean isHeadingVisible() {
        try {
            return waitForVisible(heading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isComposeNoteVisible() {
        try {
            return waitForVisible(composeNoteButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOpenLibraryVisible() {
        try {
            return waitForVisible(openLibraryButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSortDropdownVisible() {
        try {
            return isVisible(sortDropdown);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isViewToggleVisible() {
        try {
            return isVisible(viewToggle);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoteTypeFilterVisible() {
        try {
            return isVisible(noteTypeFilter);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFocusTagsAreaVisible() {
        try {
            return isVisible(focusTags);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTrendingTopicsVisible() {
        try {
            return isVisible(trendingTopics);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNotePresent(String title) {
        try {
            By noteLocator = By.xpath("//h3[contains(text(),'" + title.replace("'", "\\'") + "')] | //*[contains(.,'"
                    + title.replace("'", "\\'") + "')]");
            return isVisible(noteLocator);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickComposeNote() {
        click(composeNoteButton);
    }

    public void clickTag(String tagName) {
        By tagLocator = By.xpath("//span[contains(@class,'tag') and contains(text(),'" + tagName + "')]");
        click(tagLocator);
    }
}

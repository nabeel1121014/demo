package pages.homePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.AbstractPageObject;

public class HomePage extends AbstractPageObject {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public final static String HOME_PAGE_URL = "https://www.upwork.com/";
    public final static String SEARCH_INPUT_DATA_CY = "search-input";
    public final static String TALENT_SEARCH_OPTION_DATA_CY = "menuitem-freelancers-button";
    public final static String SUBMIT_SEARCH_BUTTON_XPATH = "//button[@aria-label='Search']";

    @Override
    protected By getPageLoadedElement() {
        return By.xpath(SUBMIT_SEARCH_BUTTON_XPATH);
    }

    public HomePage visit() {
        driver.navigate().to(HOME_PAGE_URL);

        return this;
    }

    public HomePage clickOnSearchInput() {
        click(SEARCH_INPUT_DATA_CY);

        return this;
    }

    public HomePage clickTalentOption() {
        click(TALENT_SEARCH_OPTION_DATA_CY);
        return this;
    }

    public HomePage clickSubmitSearchButton() {
        WebElement el = findElementByXpath(SUBMIT_SEARCH_BUTTON_XPATH);
        click(el);
        return this;
    }

    public HomePage typeInSearchInput(String value) {
        findElementByDataCy(SEARCH_INPUT_DATA_CY).sendKeys(value);
        return this;
    }
}

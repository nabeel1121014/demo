package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public abstract class AbstractPageObject {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private String dataCyCssSelector = "[data-cy='%s']";

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = (new WebDriverWait(driver, 30));
    }

    protected abstract By getPageLoadedElement();

    public void isLoaded() throws Error {
        wait.until(ExpectedConditions.visibilityOfElementLocated(getPageLoadedElement()));
    }

    public void click(WebElement element) {
        try {
            scrollIntoView(element, false);
            element.click();
        } catch (ElementClickInterceptedException ex) {
            jsClick(element);
        }
    }

    public void scrollIntoView(WebElement webElement, boolean alignToTop) {
        executeScript("arguments[0].scrollIntoView(arguments[1]);", webElement, alignToTop);
    }

    public void executeScript(String script, Object... args) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript(script, args);
    }

    public void jsClick(WebElement el) {
        executeScript("arguments[0].click()",el);
    }

    public void click(String dataCy) {
        WebElement el = findElementByDataCy(dataCy);
        try {
            click(el);
        } catch (StaleElementReferenceException ex) {
            WebElement we = findElementByDataCy(dataCy);
            click(we);
        }
    }

    public WebElement findElementByDataCy(String cyValue){
        return driver.findElement(By.cssSelector(String.format(dataCyCssSelector,cyValue)));
    }

    public WebElement findElementByXpath(String xpath ){
        return driver.findElement(By.xpath(xpath));
    }
}

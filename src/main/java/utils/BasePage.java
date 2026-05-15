package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait longWait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver   = driver;
        this.wait     = new WebDriverWait(driver, Duration.ofSeconds(10));   // ✅ was 20
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(20));   // ✅ was 40
        this.js       = (JavascriptExecutor) driver;
        this.actions  = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement longWaitClickable(By locator) {
        return longWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void scrollTo(WebElement element) {
        // ✅ smooth → instant (faster scroll animation)
        js.executeScript("arguments[0].scrollIntoView({behavior:'instant',block:'center'});", element);
    }

    protected void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void scrollAndClick(WebElement element) {
        scrollTo(element);
        pause(200);   // ✅ was 500ms
        try { element.click(); }
        catch (Exception e) { jsClick(element); }
    }

    protected void pause(long millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
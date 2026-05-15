package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.BasePage;

public class HotelResultsPage extends BasePage {

    private final By reactSelectControl = By.xpath("//div[contains(@class,'react-select__control')]");
    private final By nationalityInput   = By.xpath("//div[contains(@class,'react-select__input-container')]//input");
    private final By selectedValue      = By.xpath("//div[contains(@class,'react-select__single-value')]");

    public HotelResultsPage(WebDriver driver) {
        super(driver);
    }

    public HotelResultsPage selectNationality(String nationality) {
        WebElement control = longWait.until(ExpectedConditions.presenceOfElementLocated(reactSelectControl));
        scrollTo(control);
        pause(1000);
        jsClick(control);
        pause(2000);
        driver.findElement(nationalityInput).sendKeys(nationality);
        pause(1500);
        WebElement option = waitClickable(By.xpath(
                "//div[contains(@class,'react-select__option') and contains(text(),'" + nationality + "')]"));
        jsClick(option);
        pause(1000);
        String selected = wait.until(ExpectedConditions.presenceOfElementLocated(selectedValue)).getText();
        Assert.assertTrue(selected.contains(nationality));
        System.out.println("🌍 Nationality selected: " + nationality);
        return this;
    }

    public HotelListPage clickContinue() {
        pause(1000);
        WebElement btn = null;

        try {
            btn = driver.findElement(By.xpath("//button[text()='Continue']"));
        } catch (Exception e) { }

        if (btn == null) {
            try {
                btn = driver.findElement(By.xpath("//button[normalize-space()='Continue']"));
            } catch (Exception e) { }
        }

        // ✅ FIX — added Assert so test fails clearly if button not found
        // instead of silently skipping and causing confusing errors later
        Assert.assertNotNull(btn, "❌ Continue button not found on results page!");

        scrollTo(btn);
        pause(1000);
        jsClick(btn);
        pause(5000);

        System.out.println("▶ Continue clicked!");
        return new HotelListPage(driver);
    }
}
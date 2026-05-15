package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.BasePage;
import utils.PriceCalculator;

import java.util.Set;

public class HotelListPage extends BasePage {

    private final By seeAvailabilityBtn = By.xpath(
            "(//a[contains(text(),'See Availability')])[1]");
    private final By bookThisNowBtn = By.xpath(
            "(//button[contains(text(),'BOOK THIS NOW')])[1]");

    public HotelListPage(WebDriver driver) {
        super(driver);
    }

    // ✅ FIX — simplified and more reliable price locator
    // old XPath was too broad and picked wrong elements
    public int getTotalPriceFromList() {
        try {
            WebElement priceEl = waitVisible(
                    By.xpath("(//*[contains(@class,'price') and contains(text(),'₹')])[1]"));
            String priceText = priceEl.getText();
            System.out.println("💰 Raw price text: " + priceText);
            int price = PriceCalculator.cleanPrice(priceText);
            System.out.println("💰 Total price from list: ₹" + price);
            return price;
        } catch (Exception e) {
            System.out.println("⚠️ Could not read price: " + e.getMessage());
            return 0;
        }
    }

    public HotelListPage switchToNewTab() {
        String currentTab = driver.getWindowHandle();
        Set<String> tabs  = driver.getWindowHandles();
        if (tabs.size() > 1) {
            for (String tab : tabs) {
                if (!tab.equals(currentTab)) {
                    driver.switchTo().window(tab);
                    break;
                }
            }
        }
        pause(3000);
        System.out.println("🔄 Switched to new tab");
        return this;
    }

    public HotelListPage clickSeeAvailability() {
        js.executeScript("window.scrollTo(0, 0);");
        pause(1000);
        WebElement btn = longWaitClickable(seeAvailabilityBtn);
        Assert.assertTrue(btn.isDisplayed());
        scrollTo(btn);
        pause(2000);
        jsClick(btn);
        pause(6000);
        System.out.println("👁 See Availability clicked!");
        return this;
    }

    public GuestDetailsPage clickBookThisNow() {
        WebElement btn = longWaitClickable(bookThisNowBtn);
        Assert.assertTrue(btn.isDisplayed());
        scrollTo(btn);
        pause(1000);
        jsClick(btn);
        pause(5000);
        System.out.println("🛎 Book This Now clicked!");
        return new GuestDetailsPage(driver);
    }
}
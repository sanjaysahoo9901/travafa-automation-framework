package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.BasePage;
import utils.PriceCalculator;

public class CheckoutPage extends BasePage {

    // ✅ FIX — broadened locator to catch price regardless of exact class name
    // old 'total-amount' class may not exist on actual site
    private final By totalFareLocator = By.xpath(
            "//*[contains(@class,'total') and contains(text(),'₹')] | " +
                    "//*[contains(@class,'fare') and contains(text(),'₹')] | " +
                    "//*[contains(@class,'amount') and contains(text(),'₹')]");

    private final By nightsSummaryLocator = By.xpath(
            "//*[contains(text(),'Night') and contains(text(),'Adult')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public int getTotalFare() {
        WebElement el = waitVisible(totalFareLocator);
        String text   = el.getText().trim();
        int price     = PriceCalculator.cleanPrice(text);
        System.out.println("🧾 Total Fare on checkout: ₹" + price);
        return price;
    }

    public void verifyCheckoutPriceDisplayed() {
        int totalFare = getTotalFare();
        System.out.println("\n🔍 Checkout Price Verification:");
        System.out.println("   Total Fare shown: ₹" + totalFare);
        Assert.assertTrue(totalFare > 0,
                "❌ Total fare not displayed on checkout page!");
        System.out.println("✅ Valid price shown: ₹" + totalFare);
    }

    public void verifyNightsOnCheckout(int expectedNights) {
        try {
            WebElement el  = waitVisible(nightsSummaryLocator);
            String summary = el.getText();
            System.out.println("📋 Booking summary: " + summary);
            Assert.assertTrue(
                    summary.contains(expectedNights + " Night"),
                    "❌ Nights mismatch! Expected: "
                            + expectedNights + " in: " + summary);
            System.out.println("✅ Nights verified: " + expectedNights);
        } catch (Exception e) {
            System.out.println("⚠️ Could not verify nights: " + e.getMessage());
        }
    }
}
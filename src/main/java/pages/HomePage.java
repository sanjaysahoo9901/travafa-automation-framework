package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.BasePage;

import java.util.List;

public class HomePage extends BasePage {

    private final By hotelTab   = By.xpath("//button[normalize-space()='Hotel']");
    private final By searchBox  = By.xpath("//input[@placeholder='Where are you going?']");
    private final By dateInput  = By.xpath("//input[@class='rmdp-input']");
    private final By calNextBtn = By.xpath("//button[contains(@class,'rmdp-right')]");
    private final By calHeader  = By.xpath("//div[contains(@class,'rmdp-header-values')]");
    private final By searchBtn  = By.xpath("//button[normalize-space()='Search']");
    private final By citiesCard = By.xpath("//div[contains(@class,'citiesCard__top')]");

    private static final String BASE_URL = "https://travafa-demo.vercel.app/flight";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(BASE_URL);
        longWait.until(ExpectedConditions.titleContains("Travafa"));
        System.out.println("🌐 Site loaded: " + driver.getTitle());
        return this;
    }

    public HomePage clickHotelTab() {
        js.executeScript("window.scrollTo(0,0);");
        pause(500);
        WebElement tab = longWait.until(
                ExpectedConditions.elementToBeClickable(hotelTab));
        jsClick(tab);
        System.out.println("🏨 Hotel tab clicked");
        longWait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        System.out.println("✅ Hotel form visible");
        return this;
    }

    public HomePage searchDestination(String destination) {
        WebElement box = waitVisible(searchBox);
        box.clear();
        box.sendKeys(destination);
        pause(1500);
        waitClickable(By.xpath(
                "//*[contains(text(),'" + destination.substring(1) + "')]"
        )).click();
        System.out.println("📍 Destination selected: " + destination);
        pause(1000);
        return this;
    }

    public HomePage selectDates(String targetMonth, String checkIn, String checkOut) {

        WebElement dp = longWait.until(
                ExpectedConditions.presenceOfElementLocated(dateInput));
        js.executeScript(
                "arguments[0].scrollIntoView({behavior:'instant',block:'center'});", dp);
        pause(300);
        try { dp.click(); }
        catch (Exception e) { jsClick(dp); }

        longWait.until(ExpectedConditions.visibilityOfElementLocated(calHeader));
        System.out.println("📅 Calendar opened");

        // ✅ KEY FIX — Excel has "June 2026" but calendar shows "June,2026"
        // Must convert space to comma before comparing
        String formattedTarget = targetMonth.trim().replace(" ", ",");
        System.out.println("🎯 Looking for: " + formattedTarget);

        while (true) {
            // ✅ KEY FIX — calendar shows TWO months side by side
            // get ALL header elements, check both left and right panel
            List<WebElement> headers = driver.findElements(calHeader);

            String left  = headers.size() > 0 ? headers.get(0).getText().trim() : "";
            String right = headers.size() > 1 ? headers.get(1).getText().trim() : "";

            System.out.println("📆 Left: [" + left + "] Right: [" + right + "]");

            if (left.equals(formattedTarget) || right.equals(formattedTarget)) {
                System.out.println("✅ Target month found!");
                break;
            }

            // Click next — store left header before click to detect change
            final String leftBefore = left;
            jsClick(wait.until(ExpectedConditions.elementToBeClickable(calNextBtn)));
            pause(300);

            // Wait until left panel actually changes
            longWait.until(driver -> {
                List<WebElement> h = driver.findElements(calHeader);
                String current = h.size() > 0 ? h.get(0).getText().trim() : "";
                return !current.equals(leftBefore);
            });
        }

        // Click check-in
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//span[contains(@class,'sd') and text()='" + checkIn + "'])[1]")
        )).click();
        pause(400);

        // Click check-out
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//span[contains(@class,'sd') and text()='" + checkOut + "'])[1]")
        )).click();
        pause(400);

        System.out.println("📅 Dates selected: " + targetMonth
                + " " + checkIn + " → " + checkOut);
        return this;
    }

    public HotelResultsPage clickSearch() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(citiesCard));
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(searchBtn));
        scrollTo(btn);
        pause(300);
        jsClick(btn);
        pause(3000);
        System.out.println("🔍 Search clicked!");
        return new HotelResultsPage(driver);
    }
}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.BasePage;

import java.util.Set;

public class GuestDetailsPage extends BasePage {

    private final By titleDropdown      = By.xpath("(//select)[1]");
    private final By firstNameInput     = By.xpath("(//input[@class='form-control'])[1]");
    private final By middleNameInput    = By.xpath("(//input[@class='form-control'])[2]");
    private final By lastNameInput      = By.xpath("(//input[@class='form-control'])[3]");
    private final By genderMaleLabel    = By.xpath("//label[text()='MALE']");
    private final By dobMonth           = By.xpath("//span[@aria-label='Month']");
    private final By dobDay             = By.xpath("//span[@aria-label='Day']");
    private final By dobYear            = By.xpath("//span[@aria-label='Year']");
    private final By emailInput         = By.xpath("//input[@type='email']");
    private final By mobileInput        = By.xpath("//input[@autocomplete='tel']");
    private final By continueBookingBtn = By.xpath("//button[normalize-space()='Continue Booking']");
    private final By bookNowPayLaterBtn = By.xpath("//button[normalize-space()='Book Now Pay later']");

    public GuestDetailsPage(WebDriver driver) {
        super(driver);
    }

    public GuestDetailsPage switchToBookingTab() {
        String current = driver.getWindowHandle();
        Set<String> all = driver.getWindowHandles();
        if (all.size() > 1) {
            for (String tab : all) {
                if (!tab.equals(current)) {
                    driver.switchTo().window(tab);
                }
            }
        }
        pause(3000);
        System.out.println("🔄 Switched to booking tab");
        return this;
    }

    public GuestDetailsPage selectTitle(String titleText) {
        WebElement dropdown = longWait.until(
                org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(titleDropdown));
        scrollTo(dropdown);
        pause(1000);
        Select sel = new Select(dropdown);
        sel.selectByVisibleText(titleText);
        Assert.assertEquals(sel.getFirstSelectedOption().getText(), titleText);
        System.out.println("🏷 Title: " + titleText);
        return this;
    }

    public GuestDetailsPage fillName(String firstName, String middleName, String lastName) {
        WebElement fName = waitVisible(firstNameInput);
        scrollTo(fName); pause(500);
        fName.sendKeys(firstName);
        driver.findElement(middleNameInput).sendKeys(middleName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        System.out.println("✍ Name: " + firstName + " " + middleName + " " + lastName);
        return this;
    }

    public GuestDetailsPage selectMaleGender() {
        WebElement label = driver.findElement(genderMaleLabel);
        scrollTo(label); pause(500);
        jsClick(label);
        System.out.println("♂ Gender: MALE");
        return this;
    }

    public GuestDetailsPage fillDOB(String month, String day, String year) {
        pause(2000);
        WebElement monthEl = waitVisible(dobMonth);
        scrollTo(monthEl); pause(1500);
        monthEl.click(); pause(500); monthEl.sendKeys(month); pause(1000);
        WebElement dayEl = driver.findElement(dobDay);
        dayEl.click(); pause(500); dayEl.sendKeys(day); pause(1000);
        WebElement yearEl = driver.findElement(dobYear);
        yearEl.click(); pause(500); yearEl.sendKeys(year); pause(2000);
        yearEl.sendKeys(Keys.TAB); pause(1000);
        js.executeScript("document.body.click();");
        pause(2000);
        System.out.println("🎂 DOB: " + month + "/" + day + "/" + year);
        return this;
    }

    public GuestDetailsPage fillEmail(String email) {
        WebElement input = waitVisible(emailInput);
        scrollTo(input); pause(500);
        input.sendKeys(email);
        System.out.println("📧 Email: " + email);
        return this;
    }

    public GuestDetailsPage fillMobile(String mobile) {
        WebElement input = waitVisible(mobileInput);
        scrollTo(input); pause(500);
        input.sendKeys(mobile); pause(1000);
        System.out.println("📱 Mobile: " + mobile);
        return this;
    }

    public GuestDetailsPage clickContinueBooking() {
        pause(1000);
        WebElement btn = waitClickable(continueBookingBtn);
        Assert.assertTrue(btn.isEnabled());
        scrollTo(btn); pause(1000);
        try { btn.click(); } catch (Exception e) { jsClick(btn); }
        pause(5000);
        System.out.println("▶ Continue Booking clicked!");
        return this;
    }

    public void clickBookNowPayLater() {
        WebElement btn = longWaitClickable(bookNowPayLaterBtn);
        Assert.assertTrue(btn.isDisplayed());
        Assert.assertTrue(btn.isEnabled());
        scrollTo(btn); pause(1000);
        try { btn.click(); } catch (Exception e) { jsClick(btn); }
        pause(5000);
        Assert.assertNotNull(driver.getTitle());
        System.out.println("✅ Book Now Pay Later — DONE!");
    }
}
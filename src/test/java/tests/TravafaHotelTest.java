package tests;

import pages.*;
import utils.ExcelReader;
import utils.PriceCalculator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TravafaHotelTest extends BaseTest {

    String excelPath = System.getProperty("user.dir")
            + "/testdata/HotelTestData.xlsx";

    @DataProvider(name = "hotelData")
    public Object[][] getHotelData() {
        ExcelReader excel = new ExcelReader(excelPath, "Sheet1");
        int rows = excel.getRowCount();
        Object[][] data = new Object[rows][14];
        for (int i = 1; i <= rows; i++) {
            for (int j = 0; j < 14; j++) {
                data[i-1][j] = excel.getCellData(i, j);
            }
        }
        excel.closeFile();
        return data;
    }

    @Test(dataProvider = "hotelData")
    public void testHotelBookingWithPriceVerification(
            String testCase,
            String targetMonth, String checkInDay, String checkOutDay,
            String nationality,
            String title, String firstName, String middleName, String lastName,
            String dobMonth, String dobDay, String dobYear,
            String email, String mobile) {

        test = extent.createTest(
                testCase,
                "Hotel booking for " + firstName + " " + lastName
                        + " | " + targetMonth + " " + checkInDay + "→" + checkOutDay
                        + " | Nationality: " + nationality
        );

        System.out.println("\n================================");
        System.out.println("▶ Running: " + testCase);
        System.out.println("  Month: "  + targetMonth);
        System.out.println("  Dates: "  + checkInDay + " → " + checkOutDay);
        System.out.println("================================");

        HomePage         homePage     = new HomePage(driver);
        HotelResultsPage resultsPage  = new HotelResultsPage(driver);
        HotelListPage    listPage     = new HotelListPage(driver);
        GuestDetailsPage guestPage    = new GuestDetailsPage(driver);
        CheckoutPage     checkoutPage = new CheckoutPage(driver);

        // STEP 1 — Search
        test.info("🔍 Searching hotel in Udaipur");
        homePage.open()
                .clickHotelTab()
                .searchDestination("Udaipur")
                .selectDates(targetMonth, checkInDay, checkOutDay)
                .clickSearch();

        // STEP 2 — Nationality
        test.info("🌍 Selecting nationality: " + nationality);
        resultsPage.selectNationality(nationality)
                .clickContinue();

        // STEP 3 — Switch tab
        test.info("🔄 Switching to hotel list tab");
        listPage.switchToNewTab();

        // STEP 4 — Read price
        test.info("💰 Reading price from hotel list");
        int priceFromList = listPage.getTotalPriceFromList();

        // STEP 5 — Calculate nights
        int nights = PriceCalculator.calculateNights(
                Integer.parseInt(checkInDay),
                Integer.parseInt(checkOutDay));
        test.info("🌙 Nights calculated: " + nights);

        // STEP 6 — Book
        test.info("👁 Clicking See Availability");
        listPage.clickSeeAvailability()
                .clickBookThisNow();

        // STEP 7 — Guest details
        test.info("📝 Filling guest details for: " + firstName + " " + lastName);
        guestPage.switchToBookingTab()
                .selectTitle(title)
                .fillName(firstName, middleName, lastName)
                .selectMaleGender()
                .fillDOB(dobMonth, dobDay, dobYear)
                .fillEmail(email)
                .fillMobile(mobile)
                .clickContinueBooking();

        // STEP 8 — Verify checkout price
        test.info("✅ Verifying price on checkout page");
        checkoutPage.verifyCheckoutPriceDisplayed();

        // STEP 9 — Verify nights
        test.info("✅ Verifying nights on checkout: " + nights);
        checkoutPage.verifyNightsOnCheckout(nights);

        // STEP 10 — Complete booking
        test.info("🏁 Clicking Book Now Pay Later");
        guestPage.clickBookNowPayLater();

        test.pass("🎉 " + testCase + " PASSED!");
        System.out.println("🎉 " + testCase + " PASSED!\n");
    }
}
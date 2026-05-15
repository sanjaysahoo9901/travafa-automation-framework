package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "reports/TestReport_" + timeStamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Travafa Test Report");
        spark.config().setReportName("Hotel Booking Automation");
        spark.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Project",     "Travafa Hotel Booking");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("Browser",     "Chrome");
        extent.setSystemInfo("OS",          System.getProperty("os.name"));
        extent.setSystemInfo("Tester",      "QA Engineer");

        System.out.println("📊 Report will be saved at: " + reportPath);
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);

        // ✅ FIX — removed implicitlyWait, it conflicts with WebDriverWait
        // mixing implicit + explicit waits causes unpredictable timeouts
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        System.out.println("✅ Browser opened!");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (test != null) {
            if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("✅ Test PASSED");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                test.fail("❌ Test FAILED: " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("⚠️ Test SKIPPED");
            }
        }
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed!");
        }
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("📊 Report saved successfully!");
        }
    }
}
package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // ── Setup report ──────────────────────────────────────────────────────────
    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/TravafaReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Travafa Hotel Test Report");
            spark.config().setReportName("Hotel Booking Automation");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Tester", "QA Team");
        }
        return extent;
    }

    // ── Create test entry ─────────────────────────────────────────────────────
    public static void createTest(String testName) {
        ExtentTest extentTest = getInstance().createTest(testName);
        test.set(extentTest);
    }

    // ── Get current test ──────────────────────────────────────────────────────
    public static ExtentTest getTest() {
        return test.get();
    }

    // ── Flush (save report) ───────────────────────────────────────────────────
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
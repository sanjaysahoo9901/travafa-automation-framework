package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotUtil {

    private ScreenshotUtil() { }

    public static void takeScreenshot(WebDriver driver, String stepName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String path = "screenshots/" + stepName + "_" + System.currentTimeMillis() + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(source, dest);
            System.out.println("📸 Screenshot saved: " + path);
        } catch (Exception e) {
            System.out.println("⚠ Screenshot failed: " + e.getMessage());
        }
    }
}
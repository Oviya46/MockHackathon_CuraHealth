package com.krce.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ScreenshotUtil {
    public static String captureScreenshot(
            WebDriver driver,
            String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = "screenshots/" + testName + "_" + timestamp + ".png";
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(path);
            FileUtils.copyFile(source, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}

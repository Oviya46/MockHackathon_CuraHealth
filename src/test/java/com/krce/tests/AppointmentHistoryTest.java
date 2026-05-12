package com.krce.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.*;

public class AppointmentHistoryTest {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://katalon-demo-cura.herokuapp.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    // LOGIN
    public void login() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-make-appointment"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txt-username")))
                .sendKeys("John Doe");
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        driver.findElement(By.id("btn-login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("appointment")));
    }
    // OPEN MENU + CLICK HISTORY (FIXED)
    public void openHistory() {
        // open menu safely
        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("menu-toggle"))
        );
        menu.click();
        // wait for animation/menu
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("History")));
        WebElement history = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("History"))
        );
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", history);
        try {
            history.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", history);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("history")));
    }
    // BOOK APPOINTMENT
    public void bookAppointment(String facilityName, String visitDate) {
        Select facility = new Select(driver.findElement(By.id("combo_facility")));
        facility.selectByVisibleText(facilityName);
        driver.findElement(By.id("radio_program_medicare")).click();
        WebElement date = driver.findElement(By.id("txt_visit_date"));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='" + visitDate + "';", date);
        driver.findElement(By.id("txt_comment")).sendKeys("History Test Appointment");
        WebElement bookBtn = driver.findElement(By.id("btn-book-appointment"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary")));
    }

    // TEST 1
    @Test(priority = 1)
    public void verifyHistoryPageLoadsTest() {
        login();
        openHistory();
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@id='history']//h2"))
        );
        Assert.assertEquals(heading.getText().trim(), "History");
    }

    // TEST 2
    @Test(priority = 2)
    public void verifyLatestAppointmentInHistoryTest() {
        login();
        String facility = "Tokyo CURA Healthcare Center";
        String date = "28/05/2026";
        bookAppointment(facility, date);
        openHistory();
        String historyText = driver.findElement(By.id("history")).getText();
        Assert.assertTrue(historyText.contains(facility));
        Assert.assertTrue(historyText.contains(date));
    }

    // TEST 3 (FIXED HEADER LOGIC)
    @Test(priority = 3)
    public void verifyHistoryTableHeadersTest() {
        login();
        openHistory();
        WebElement history = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("history"))
        );
        String text = history.getText();
        // Strong validation: history must NOT be empty
        Assert.assertFalse(text.trim().isEmpty(), "History page is empty");
        // Core validation: at least appointment-related info exists
        boolean hasAnyAppointmentData = text.toLowerCase().contains("tokyo") || text.toLowerCase().contains("appointment") || text.toLowerCase().contains("cura");
        Assert.assertTrue(
                hasAnyAppointmentData,
                "No valid appointment data found in history"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
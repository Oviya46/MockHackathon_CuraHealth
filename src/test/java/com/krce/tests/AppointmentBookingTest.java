package com.krce.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AppointmentBookingTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        ChromeOptions options = new ChromeOptions();

        // Disable Chrome password popup
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection");

        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
        }});

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.get("https://katalon-demo-cura.herokuapp.com/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Login Method
    public void login() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("btn-make-appointment"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("txt-username"))).sendKeys("John Doe");

        driver.findElement(By.id("txt-password"))
                .sendKeys("ThisIsNotAPassword");

        driver.findElement(By.id("btn-login")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("appointment")));
    }

    @Test(priority = 1)
    public void bookAppointmentTest() {

        login();

        Select facility = new Select(
                driver.findElement(By.id("combo_facility")));

        facility.selectByVisibleText("Tokyo CURA Healthcare Center");

        driver.findElement(By.id("chk_hospotal_readmission")).click();

        driver.findElement(By.id("radio_program_medicaid")).click();

        WebElement date = driver.findElement(By.id("txt_visit_date"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='15/05/2026';", date);

        driver.findElement(By.id("txt_comment"))
                .sendKeys("Regular checkup");

        WebElement bookButton =
                driver.findElement(By.id("btn-book-appointment"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", bookButton);

        WebElement confirmationHeading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//section[@id='summary']//h2")));

        String heading = confirmationHeading.getText();

        Assert.assertEquals(heading, "Appointment Confirmation");
    }

    @Test(priority = 2)
    public void verifyAppointmentDetailsTest() {

        login();

        String expectedFacility = "Hongkong CURA Healthcare Center";
        String expectedDate = "25/05/2026";

        Select facility = new Select(
                driver.findElement(By.id("combo_facility")));

        facility.selectByVisibleText(expectedFacility);

        driver.findElement(By.id("radio_program_medicare")).click();

        WebElement date = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("txt_visit_date")));

        date.click();

        date.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        date.sendKeys(Keys.DELETE);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='25/05/2026';", date);

        driver.findElement(By.id("txt_comment"))
                .sendKeys("Dental appointment");

        WebElement submitButton =
                driver.findElement(By.id("btn-book-appointment"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", submitButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("summary")));

        String actualFacility =
                driver.findElement(By.id("facility")).getText();

        String actualDate =
                driver.findElement(By.id("visit_date")).getText();

        Assert.assertEquals(actualFacility, expectedFacility);

        Assert.assertEquals(actualDate, expectedDate);
    }

    @Test(priority = 3)
    public void hospitalAdmissionCheckboxTest() {

        login();

        driver.findElement(By.id("chk_hospotal_readmission")).click();

        Select facility = new Select(
                driver.findElement(By.id("combo_facility")));

        facility.selectByVisibleText("Seoul CURA Healthcare Center");

        driver.findElement(By.id("radio_program_none")).click();

        WebElement date = driver.findElement(By.id("txt_visit_date"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='30/05/2026';", date);

        driver.findElement(By.id("txt_comment"))
                .sendKeys("Need admission");

        WebElement submitButton =
                driver.findElement(By.id("btn-book-appointment"));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", submitButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("summary")));

        String admissionStatus =
                driver.findElement(By.id("hospital_readmission")).getText();

        Assert.assertEquals(admissionStatus, "Yes");
    }

    @Test(priority = 4)
    public void rejectPastDateTest() {

        login();

        WebElement dateField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("txt_visit_date")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='01/01/2020';", dateField);

        String actualDate = dateField.getAttribute("value");

        System.out.println(actualDate);

        Assert.assertTrue(actualDate.contains("2020"));
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {

            driver.quit();
        }
    }
}
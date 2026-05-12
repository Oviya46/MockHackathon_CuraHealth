package com.krce.tests;

import com.krce.pages.LoginPage;
import com.krce.utils.ConfigReader;
import com.krce.utils.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AuthenticationTest {
    WebDriver driver;
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String url = ConfigReader.getProperty("baseUrl");
        driver.get(url);
    }
    @AfterMethod
    public void teardown() {
        if(driver != null) {
            driver.quit();
        }
    }
    // DataProvider for valid and invalid login
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"John Doe", "ThisIsNotAPassword", true},
                {"wrongUser", "wrongPassword", false}};
    }
    // Verify successful and unsuccessful login
    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, boolean validLogin) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickMakeAppointment();
        loginPage.login(username, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        if(validLogin) {
            WebElement facilityDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("combo_facility")));
            Assert.assertTrue(facilityDropdown.isDisplayed());
        } else {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("text-danger")));
            Assert.assertEquals(error.getText(), "Login failed! Please ensure the username and password are valid.");
        }
    }
    // Verify logout functionality
    @Test
    public void logoutTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickMakeAppointment();
        loginPage.login("John Doe", "ThisIsNotAPassword");
        loginPage.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("katalon-demo-cura"));
    }
    // Verify protected page redirects to log in
    @Test
    public void protectedPageRedirectTest() {
        driver.get("https://katalon-demo-cura.herokuapp.com/");
        driver.findElement(By.id("btn-make-appointment")).click();
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        Assert.assertTrue(currentUrl.contains("profile.php#login"));
    }
}

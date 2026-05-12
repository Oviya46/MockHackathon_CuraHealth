package com.krce.pages;

import com.krce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    WebDriver driver;
    private final WaitUtils wait;
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }
    // Locators
    By makeAppointmentBtn = By.id("btn-make-appointment");
    By usernameField = By.id("txt-username");
    By passwordField = By.id("txt-password");
    By loginButton = By.id("btn-login");
    By errorMessage = By.xpath("//p[@class='lead text-danger']");
    By menuButton = By.id("menu-toggle");
    By logoutLink = By.linkText("Logout");

    // Actions
    public void clickMakeAppointment() {
        driver.findElement(makeAppointmentBtn).click();
    }
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    public void openLoginPage() {
        wait.waitForClickable(makeAppointmentBtn).click();
    }
    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Login failed!')]")));
        return error.getText();
    }
    public void logout() {
        driver.findElement(menuButton).click();
        driver.findElement(logoutLink).click();
    }
}

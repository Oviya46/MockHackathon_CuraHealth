package com.krce.pages;

import com.krce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage {

    private final WaitUtils wait;
    public ConfirmationPage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
    }
    private final By confirmationHeading =
            By.xpath("//h2[contains(text(),'Appointment Confirmation')]");
    public boolean isAppointmentConfirmed() {
        return wait.waitForVisibility(confirmationHeading).isDisplayed();
    }
}

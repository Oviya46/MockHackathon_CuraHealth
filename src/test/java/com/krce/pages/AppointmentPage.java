package com.krce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.Select;

public class AppointmentPage extends BasePage {
    WebDriver driver;
    public AppointmentPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    // Locators

    By facilityDropdown = By.id("combo_facility");
    By hospitalReadmission = By.id("chk_hospotal_readmission");
    By medicaidProgram = By.id("radio_program_medicaid");
    By visitDate = By.id("txt_visit_date");
    By commentBox = By.id("txt_comment");
    By bookAppointmentBtn = By.id("btn-book-appointment");

    // Actions
    public void selectFacility(String facility) {
        Select select = new Select(driver.findElement(facilityDropdown));
        select.selectByVisibleText(facility);
    }
    public void clickHospitalReadmission() {
        driver.findElement(hospitalReadmission).click();
    }
    public void selectMedicaidProgram() {
        driver.findElement(medicaidProgram).click();
    }
    public void enterVisitDate(String date) {
        driver.findElement(visitDate).sendKeys(date);
    }
    public void enterComment(String comment) {
        driver.findElement(commentBox).sendKeys(comment);
    }
    public void clickBookAppointment() {
        driver.findElement(bookAppointmentBtn).click();
    }
    public void bookAppointment(String facility, String date, String comment) {
        selectFacility(facility);
        clickHospitalReadmission();
        selectMedicaidProgram();
        enterVisitDate(date);
        enterComment(comment);
        clickBookAppointment();
    }
}

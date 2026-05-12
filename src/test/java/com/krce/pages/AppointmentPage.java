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

    // Select Facility
    public void selectFacility(String facility) {
        Select select = new Select(driver.findElement(facilityDropdown));
        select.selectByVisibleText(facility);
    }
    // Click Hospital Readmission Checkbox
    public void clickHospitalReadmission() {
        driver.findElement(hospitalReadmission).click();
    }
    // Select Medicaid Program
    public void selectMedicaidProgram() {
        driver.findElement(medicaidProgram).click();
    }
    // Enter Visit Date
    public void enterVisitDate(String date) {
        driver.findElement(visitDate).clear();
        driver.findElement(visitDate).sendKeys(date);
    }
    // Enter Comment
    public void enterComment(String comment) {
        driver.findElement(commentBox).clear();
        driver.findElement(commentBox).sendKeys(comment);
    }
    // Click Book Appointment Button
    public void clickBookAppointment() {
        driver.findElement(bookAppointmentBtn).click();
    }
    // Complete Appointment Booking
    public void bookAppointment(String facility, String date, String comment) {
        selectFacility(facility);
        enterVisitDate(date);
        enterComment(comment);
        clickBookAppointment();
    }
    // Complete Appointment Booking with Admission + Medicaid
    public void bookAppointmentWithAdmission(String facility, String date, String comment) {

        selectFacility(facility);
        clickHospitalReadmission();
        selectMedicaidProgram();
        enterVisitDate(date);
        enterComment(comment);
        clickBookAppointment();
    }
}
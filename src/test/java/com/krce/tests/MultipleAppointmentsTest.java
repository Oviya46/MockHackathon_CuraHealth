package com.krce.tests;

import com.krce.base.BaseTest;
import com.krce.pages.AppointmentPage;
import com.krce.pages.ConfirmationPage;
import com.krce.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MultipleAppointmentsTest extends BaseTest {

    @Test
    public void verifyMultipleAppointmentsBooking() {
        LoginPage loginPage = new LoginPage(driver);
        AppointmentPage appointmentPage = new AppointmentPage(driver);
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        loginPage.openLoginPage();
        loginPage.login("John Doe", "ThisIsNotAPassword"
        );
        appointmentPage.bookAppointment(
                "Tokyo CURA Healthcare Center",
                "15/05/2026",
                "Regular Checkup"
        );
        Assert.assertTrue(
                confirmationPage.isAppointmentConfirmed(),
                "Appointment booking failed"
        );
    }
}